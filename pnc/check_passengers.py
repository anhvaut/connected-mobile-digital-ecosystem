#!/usr/bin/env python
#

import sys
import string
import subprocess
import gps, os, time, commands
import datetime
import httplib
import urllib2
import ConfigParser
import logging
import logging.handlers
from suds.transport import HttpTransport
from suds.client import Client
from suds.options import Options

logger = logging.getLogger("__main__")        

def getClient(url, key, cert):

    transport = HttpClientAuthTransport(key, cert)

    return Client(url, transport = transport)

#SUDS Client Auth solution
class HttpClientAuthTransport(HttpTransport):

    def __init__(self, key, cert, options = Options()):

        HttpTransport.__init__(self, options)

        self.urlopener = urllib2.build_opener(HTTPSClientAuthHandler(key, cert))

class HTTPSClientAuthHandler(urllib2.HTTPSHandler):

    def __init__(self, key, cert):

        urllib2.HTTPSHandler.__init__(self)
        
        self.key = key
        
        self.cert = cert
        
    def https_open(self, req):

        return self.do_open(self.getConnection, req)
    
    def getConnection(self, host):
        
        return httplib.HTTPSConnection(host, key_file=self.key, cert_file=self.cert)

def open_secure_link():
    config = ConfigParser.RawConfigParser()
    config.read('/home/unishuttle01/busLocation.cfg')
    url = config.get("Web","url")
    logger.debug( "open_secure_link: url=%s", url)
    keyFileName = config.get("KeyFiles","keyFile")
    certFileName = config.get("KeyFiles","certificateFile")
    global busClient
    try:
        busClient = getClient(url, keyFileName, certFileName)
        #print busClient
    except:
        retry = 0
        while 1:
            try:
                retry += 1
                busClient = getClient(url, keyFileName, certFileName)
                logger.debug( "open_secure_link: got busClient")
                break
            except:
                logger.debug( "open_secure_link: exception caught no connection is setup")
                if retry == 60:
                        sys.exit()
            else:
                break
            time.sleep(10)
    
def get_gps_coord():

    global longitude
    global latitude
    global time_stamp
    #module_logger = logging.getLogger('get_gps_coord').setLevel(logging.DEBUG)

    while 1:
        session.poll()
        session.read()

        if gps.PACKET_SET:
    
            session.stream()

            #print 'latitude    ' , session.fix.latitude
            #print 'longitude   ' , session.fix.longitude
            #print 'time utc    ' , session.fix.time
        longitude = session.fix.longitude
        latitude = session.fix.latitude

        if (longitude != 0.0) and (latitude != 0.0):
            longitude = session.fix.longitude
            latitude = session.fix.latitude
            speed = str(float(session.fix.speed)*1.85)
            try:
                time_stamp = datetime.datetime.now()
                # print busClient
                result = busClient.service.insert("unishuttle01",longitude,latitude,str(time_stamp),speed)
                logger.debug("get_gps_coord: Normal Upload of bus Location (%s,%s) time= %s speed= %s with result = %s",longitude,latitude,str(time_stamp),speed, result)
                break
            except:
                logger.debug("get_gps_coord: exception Caught while Uploading bus Location (%s,%s) time= %s speed= %s, open_secure_link again",longitude,latitude,str(time_stamp),speed)
                open_secure_link()
                time_stamp = datetime.datetime.now()
                result = busClient.service.insert("unishuttle01",longitude,latitude,str(time_stamp),speed)
                logger.debug("get_gps_coord: 2nd Upload bus Location (%s,%s) time= %s speed= %s with result= %s",longitude,latitude,str(time_stamp),speed,result)
                 
        time.sleep(1)

class passenger:
    def __init__(self, macAddress,lat,lng, on_off, time):
        self.MACAddress = str(macAddress)
        self.latitude = lat
        self.longitude = lng
        self.on_off = on_off
        self.time = time

    def __call__(self,macAddress,lat,lng, on_off, time):
        self.__init__(macAddress,lat,lng, on_off, time)
        return self

    def __repr__(self):
       st = "\n%s:     (%f, %f)     %s" % (self.MACAddress, self.latitude,self.longitude, self.time)
       return st

if __name__ == '__main__':
    
    
    MACtable = []
    Passengers = []
    #logging.basicConfig()
    #logging.getLogger('suds.client').setLevel(logging.DEBUG)
    #logging.getLogger('suds.transport').setLevel(logging.DEBUG)
    log_hdlr = logging.handlers.RotatingFileHandler("/var/log/checkpassenger.log","a",5000000,6)
    formatter = logging.Formatter('%(asctime)s - %(message)s')
    log_hdlr.setFormatter(formatter)
    logger.addHandler(log_hdlr)
    logger.setLevel(logging.DEBUG)
    time_stamp = datetime.datetime.now()
    #global busClient
    open_secure_link()
    session = gps.gps(mode = gps.WATCH_ENABLE|gps.WATCH_NEWSTYLE|gps.WATCH_JSON)
    counter = 0
    
    while 1:
        counter = counter +1
        arp = subprocess.Popen(['/usr/sbin/arp', '-vn'], stdout=subprocess.PIPE,)
        grep = subprocess.Popen(['/bin/grep', 'wlan0'], stdin=arp.stdout, stdout=subprocess.PIPE,)
        #awk = subprocess.Popen(['awk','\'{print $3}\'',stdin=grep.stdout,stdout=subprocess.PIPE,)
        end_of_pipe=grep.stdout
        logger.debug("__main__: getting macaddress using arp -vn")
        #while 1:
        #line = proc.readline()
        for line in end_of_pipe:
            #logger.debug("__main__: arp -vn line= %s",line)
            if string.find(line, "wlan0") != -1:
                entry = line.split()
                if len(entry) == 5:
                    MACtable.append(str(entry[2]))
                    logger.debug("__main__: MACAddress %s added to MACtable",str(entry[2]))
            #if not line: break
            #proc.wait()
        tmp_Passengers = Passengers[:]

        i = 0
        for p in tmp_Passengers:
            i=i+1
            mMacAddress = str(p.MACAddress)
            if not mMacAddress in MACtable:
                # update Passenger table on server, passenger leaves bus
                logger.debug("__main__: For loop 1: removing passenger from server: %s has left bus", str(p.MACAddress))
                try:
                    
                    result = busClient.service.insertPessangerMAC("unishuttle01", longitude,latitude,str(datetime.datetime.now()),0,mMacAddress)
                    logger.debug("__main__: For loops 1: removing paessager from server with result : %s", str(result))
                except:
                    open_secure_link()
                    logger.debug("__main__: For loop 1: exception caught while MACAddress %s from server",str(mMacAddress))
                    result = busClient.service.insertPessangerMAC("unishuttle01", longitude,latitude,str(datetime.datetime.now()),0,mMacAddress)
                    logger.debug("__main__: For loops 1: removing passenger from server with result : %s", str(result))
                Passengers.remove(p)
                #print "For 1: Passengers after removing passenger: ", Passengers
            else:
                # Passenger still on the bus remove from the MAC_table
                logger.debug("__main__: For loop 1: removing mMacAddress %s from MACtable still on bus: ", str(mMacAddress))
                MACtable.remove(mMacAddress)
                #print "For 1: MACtable after removing mMacAddress: ", MACtable
        try:
            get_gps_coord()
        except:
            logger.debug("__main__: caught exception while getting GPS_coords try again")
            session = gps.gps(mode = gps.WATCH_ENABLE|gps.WATCH_NEWSTYLE|gps.WATCH_JSON)
            get_gps_coord()
            
        # Add new MACAddresses to Passenger table
        tmp_MACtable = MACtable[:]
        #if len(MACtable) > 0:
        #    print len(MACtable)," of MACAddr still in MACtable",  MACtable
        j = 0
        
        for MACAddress in tmp_MACtable:
            j=j+1
            #print "For 2: j = ", j
            #print "For 2: j = ",j, MACAddress, "(",latitude, ",", longitude, ")"

            #update server, new passenger joined bus
            try:
                logger.debug("__main__: For loop 2: update MACAddress %s on server", str(MACAddress))
                result = busClient.service.insertPessangerMAC("unishuttle01", longitude,latitude,str(datetime.datetime.now()),1,MACAddress)
                logger.debug("__main__: For loops 2: add passenger to server with result : %s", str(result))
            except:
                open_secure_link()
                logger.debug("__main__: For loop 2: exception caught while update MACAdress %s, try again", str(MACAddress))
                result = busClient.service.insertPessangerMAC("unishuttle01", longitude,latitude,str(datetime.datetime.now()),1,MACAddress)
                logger.debug("__main__: For loops 2: add passenger to server with result : %s", str(result))

            Passengers.append(passenger(MACAddress, latitude, longitude, 1, str(datetime.datetime.now())))
            #print "For 2: Adding new mPassenger ", mPassenger
            MACtable.remove(MACAddress)
            #print "For 2: removed MACAddress", MACAddress
            #print "For 2: Current Mactable: ", MACtable
    
        logger.debug("__main__: %d Passengers",len(Passengers))
        # print "current time ", datetime.datetime.now(), "time_stamp ", time_stamp
        t1= datetime.datetime.now() - time_stamp
        logger.debug ("__main__: Loop counter = %d timediff = %d", counter, t1.seconds)
        #self._logger.debug "waiting ...\n"
        if t1.seconds < 10:
            time.sleep(10 - t1.seconds)
        #printf('%15s %15s %15s \n',ip, mac, iface)
        
