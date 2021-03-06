#!/usr/bin/env python
#
# Version 1.0 : Edward Dou 2011/06/13
#               initial version deployed on bus using one single main thread every ten seconds
#               check mac addresses attached to wlan0 using arp -vn if there are changes then
#               update update server by calling insertpassengerMAC (fifth field) 1: is new MAC 0: MAC no longer in the list
#               Also update server by calling get_gps_coord with the lasted GPS information
#
# Version 2.0 : Edward Dou 2011/09/07
#               Fix issue with GPSd buffer. While sleeping for 10sec (could be more if the update to server takes longer)
#               the socket connection to GPSd is flushed, gps information read are still 10 seconds earlier,
#               split the retrieval gps process to a seperate thread so we obtain fresh gps information
#               new class GpsPolller is added to perform this function
#               Also update the insert method with "heading" information.
#               Add handling for the extra return information from server by writing to /tmp
#               "restart" will restart this process
#               "reboot" will reboot machine
#               "email" will email last 50 lines of debug mail
#               Update watchdog script so it can handle the above
#               Create new email script and setup cron job for every 1 hour, the last 50 lines of debug log is sent
#               Add handling for specfic exceptions handling

import sys, traceback
import math
import string
import subprocess
import gps, os, time, commands, socket
import datetime
import httplib
import urllib2
import ConfigParser
import logging
import logging.handlers
import threading
import suds
import MySQLdb as mdb
from suds.transport import HttpTransport
from suds.client import Client
from suds.options import Options

#pcn import library
import serial, binascii
from threading import Thread
from time import sleep
from ctypes import string_at
from sys import getsizeof
from binascii import hexlify
from pcncommand import PCNCommand
from commandanalyzer import CommandAnalyzer
from stopactivity import StopActivities
from csvpassenger import CSVPassenger

stopActivities = StopActivities()
#end pcn


logger = logging.getLogger("__main__")
hostname = socket.gethostname()        

def getClient(url, key, cert):

    transport = HttpClientAuthTransport(key, cert)

    return Client(url, transport = transport)

#SUDS Client Auth solution
class HttpClientAuthTransport(HttpTransport):

    def __init__(self, key, cert, options = Options()):

        HttpTransport.__init__(self)

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

def toRad(deg):
    return deg*math.pi/180

def toDeg(rad):
    return rad*180/math.pi

def find_heading(lat_prev,lng_prev,lat,lng):
    
    lat1 = toRad(lat_prev)
    lat2 = toRad(lat)
    dLon = toRad(lng-lng_prev)
      
    dPhi = math.log(math.tan(lat2/2+math.pi/4)/math.tan(lat1/2+math.pi/4))
    if (math.fabs(dLon) > math.pi) :
        if dLon > 0:
            dLon = 2*math.pi-dLon
        else :
            dLong = 2*math.pi+dLon
    brng = math.atan2(dLon, dPhi)
      
    return (toDeg(brng)+360) % 360

# V2.0 GpsPoller class to handle gps data gathering    
class GpsPoller(threading.Thread):
    # this thread will read gpsd buffer every 0.2 sec, this will clear the buffer from gpsd, making sure
    # the data read on the main thread are current

   def __init__(self):
       threading.Thread.__init__(self)
       self.session = gps.gps(mode = gps.WATCH_ENABLE|gps.WATCH_NEWSTYLE|gps.WATCH_JSON)
       logger.debug("GpsPoller: __init__ create session with gpsd")
       print 'GpsPoller: __init__ create session with gpsd'
       #self.session = gps(mode=WATCH_ENABLE)
       self.latitude = gps.NaN
       self.longitude = gps.NaN
       self.speed = gps.NaN
       self.time = gps.NaN
       self.heading = gps.NaN
       self.lat_prev = gps.NaN
       self.lng_prev = gps.NaN
        
   def run(self):
       # will keep retry to get gps data after 10sec sleep
       x = 0
       while 1:
            while True:
                # request next report in the buffer so there is no build up of data in gps pipe
                try:
                    self.current_value = self.session.next()
                    if x == 30 :
                        logger.debug("__GpsPoller__:%s",self.current_value);
                        x = 0
                    #else :
                        #logger.debug("GpsPoller: x=%d",x);
                    x +=1
                    #print self.current_value
                    if (self.session.valid & gps.LATLON_SET) :
                        self.longitude = self.session.fix.longitude
                        self.latitude = self.session.fix.latitude
                        self.speed = (self.session.fix.speed)*1.85
                        self.heading = self.session.fix.track
                        # if heading information is not available from GPS, then calculate using previous point
                        if (self.heading == gps.NaN):
                            if (self.lat_prev == gps.NaN):
                                self.lat_prev = 0.0
                                self.lng_prev = 0.0
                            else:
                                self.heading = find_heading(self.lat_prev, self.lng_prev, self.latitude, self.longitude)
                                self.lat_prev = self.latitude
                                self.lng_prev = self.longitude
                        self.time = self.session.fix.time
                        #print "GpsPoller: getting ONLINE_SET GPSdata long=" ,self.longitude, "lat= ", self.latitude
                        #print "timeis ", self.time," ",self.speed,"\n"
    #                else :
    #                    self.longitude = self.session.fix.longitude
    #                    self.latitude = self.session.fix.latitude
    #                    print "GpsPoller: no ONLINE_SET GPSdata long=%f lat=%f" ,self.longitude, "lat= ", self.latitude
                    time.sleep(0.2) # tune this, you might not get values that quickly
#                except StopIteration:
                except :
    #                logger.debug("__GpsPoller__: StopIteration has been recieved, wait for 10 sec and start the GPS session again")
		    print '__GpsPoller__: StopIteration has been recieved, wait for 10 sec and start the GPS session again'
                    self.session = gps.gps(mode = gps.WATCH_ENABLE|gps.WATCH_NEWSTYLE|gps.WATCH_JSON)
                    self.latitude = gps.NaN
                    self.longitude = gps.NaN
                    self.speed = gps.NaN
                    self.time = gps.NaN
                    break
#                except:
#                    logger.exception("GpsPoller: execption has been caught wait for 10s and try again")
#                    break
            time.sleep(10)
            logger.debug("__GpsPoller__: outter while loop waited 10 sec and start session again")
	    print '__GpsPoller__: outter while loop waited 10 sec and start session again'
      #      self.session = gps.gps(mode = gps.WATCH_ENABLE|gps.WATCH_NEWSTYLE|gps.WATCH_JSON)

# V3.0 DBupdate class to handle update gps data to local database    
class DBupdate(threading.Thread):
    # this thread will update GPS information every 1 sec
    def __init__(self):
        threading.Thread.__init__(self)
        self.prevLat = gpsp.latitude
        self.prevLng = gpsp.longitude
        logger.debug("DBupdate: __init__ database connection")

    def run(self):
        i = 0
        while 1:
            i+=1
            sqlInsert="INSERT INTO BusLocation (bus_id,longitude,latitude,time_stamp,speed,heading) VALUES ('%s','%f','%f','%s','%f','%f')"
            try:
                if (self.prevLat == gpsp.latitude) and (self.prevLng == gpsp.longitude):
                    conn = mdb.connect('localhost', 'root', 'hollyzhu', 'busLocation');
                    cursor = conn.cursor()
                    cursor.execute(sqlInsert % (hostname, gpsp.longitude,gpsp.latitude,str(datetime.datetime.now()),gpsp.speed,gpsp.heading))
                    conn.commit()
                    cursor.close()
                    conn.close()
                    if i == 10:
                        logger.debug("DBupdate: logging every 10th update (%f,%f)",gpsp.longitude,gpsp.latitude)
                        i=1
                self.prevLat = gpsp.latitude
                self.prevLng = gpsp.longitude
                
#            except mdb.Error, e:
#                logger.debug("DBupdate Error %d: %s" %(e.args[0], e.args[1]))
            except:
                logger.debug("DBupdate exception caught")

            time.sleep(1)
            
       
def open_secure_link():
    config = ConfigParser.RawConfigParser()
    config.read('/home/%s/busLocation.cfg' % hostname)
#   Change for mac testing
#    config.read('/Users/edou/Documents/unishuttle01_data/busLocation_mac.cfg')
    url = config.get("Web","url")
    logger.debug( "open_secure_link: url=%s", url)
    keyFileName = config.get("KeyFiles","keyFile")
    certFileName = config.get("KeyFiles","certificateFile")
    global busClient
#    try:
#        busClient = getClient(url, keyFileName, certFileName)
        #print busClient
#    except suds.WebFault, e:
#        print e
    while 1:
        try:
#            logger.debug("open_seurec_link: trying to setup connection to server")
            busClient = getClient(url, keyFileName, certFileName)
            logger.debug( "open_secure_link: busClient has been setup")
            break
        #except suds.WebFault, e:
#            logger.debug("open_secure_link: suds.WebFault")
#            print e
#            logger.exception( "open_secure_link: suds.WebFault exception is caught wait for 10s and try again")
        except:
            logger.debug("open_secure_link: failed with exception wait for 10s and try again")
        else:
            break
        time.sleep(10)
        
# V2.0 : this function has been replaced by GPSPoller        
##def get_gps_coord():
##
##    global longitude
##    global latitude
##    global time_stamp
##    #module_logger = logging.getLogger('get_gps_coord').setLevel(logging.DEBUG)
##
##    while 1:
##        session.poll()
##        session.read()
##
##        if gps.PACKET_SET:
##    
##            session.stream()
##
##            #print 'latitude    ' , session.fix.latitude
##            #print 'longitude   ' , session.fix.longitude
##            #print 'time utc    ' , session.fix.time
##        longitude = session.fix.longitude
##        latitude = session.fix.latitude
##
##        if (longitude != 0.0) and (latitude != 0.0):
##            longitude = session.fix.longitude
##            latitude = session.fix.latitude
##            speed = str(float(session.fix.speed)*1.85)
##            try:
##                time_stamp = datetime.datetime.now()
##                # print busClient
##                result = busClient.service.insert(hostname,longitude,latitude,str(time_stamp),speed)
##                logger.debug("get_gps_coord: Normal Upload of bus Location (%s,%s) time= %s speed= %s",longitude,latitude,str(time_stamp),speed)
##                break
##            except:
##                logger.debug("get_gps_coord: exception Caught while Uploading bus Location (%s,%s) time= %s speed= %s, open_secure_link again",longitude,latitude,str(time_stamp),speed)
##                open_secure_link()
##                time_stamp = datetime.datetime.now()
##                result = busClient.service.insert(hostname,longitude,latitude,str(time_stamp),speed)
##                logger.debug("get_gps_coord: 2nd Upload bus Location (%s,%s) time= %s speed= %s successful",longitude,latitude,str(time_stamp),speed)
##                 
##        time.sleep(1)
##        self.latitude = lat
##        self.longitude = lng
##        self.on_off = on_off
##        self.time = time

#=====================================================
#send command to pcn
#=====================================================
def sendCommand(ser):
	while True:
		#print 'send:'
		print 'send gcounters:',ser.write(PCNCommand.GET_COUNTERS)
		sleep(5)

#=====================================================
#waiting to read command
#=====================================================
def receiveCommand(ser,latitude,longitude):
	i = 0
	while True:
		try:
			#print 'receive:'
			data = ser.read(300)
			if(len(data)>0):
				commandAnalyzer = CommandAnalyzer(data)
				if (commandAnalyzer.getCommandName() == 'gcounters'):
					commandAnalyzer.printCommandInfo()
					#busClient.service.insertpassengerMAC('unishuttle04', latitude,longitude,str(datetime.datetime.now()),1,2)
					passengerOn = commandAnalyzer.getNumberPassengerOn()
					passengerOff = commandAnalyzer.getNumberPassengerOff()
					if(passengerOn !=0 or passengerOff !=0):
						print 'insert values(source,destination,datetime,on,off,lat,lon)',commandAnalyzer.getSource(),commandAnalyzer.getDestination(), str(datetime.datetime.now()), passengerOn, passengerOff, latitude, longitude
						logger.debug("__PCN receive data__: normal receive data")
						#busClient.service.insertpassengerMAC('unishuttle04', latitude,longitude,str(datetime.datetime.now()),1,passengerOn)
						#busClient.service.insertpassengerMAC('unishuttle04', latitude,longitude,str(datetime.datetime.now()),0,passengerOff)
						print 'send reset:',ser.write(PCNCommand.RESET)
		except:
			print 'receive data from pcn failed'
			logger.debug("__PCN receive data__: receive data from pcn failed")

		sleep(1)

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
    
    gpsp = GpsPoller()
    gpsp.start()

    #sleep(10)

    ser = serial.Serial('/dev/pcn', 115200, timeout=0)

    ser.flushInput()
    ser.flushOutput()
    
    #ser.write(PCNCommand.ENABLE_COUNTING)

    #=====================================================
    #create thread for sending and receiving command
    #=====================================================
    Thread(target=receiveCommand, args=(ser,gpsp.latitude,gpsp.longitude)).start()
    Thread(target=sendCommand, args=(ser,)).start()  

    
    #print 'send gcounters:',ser.write(PCNCommand.GET_COUNTERS)
    
    

    while 1:
	#print 'receive:'
	#data = ser.read(300)
	#if(len(data)>0):
	#	print 'got data'
	
	sleep(0.5)
        
