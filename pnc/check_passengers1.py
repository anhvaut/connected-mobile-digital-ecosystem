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

ser = serial.Serial('/dev/ttyUSB7', 115200, timeout=0)
stopActivities = StopActivities()
#end pcn

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

#=====================================================
#send command to pcn
#=====================================================
def sendCommand(ser):
	while True:
		#print 'send:'
		print 'send gcounters:',ser.write(PCNCommand.GET_COUNTERS)
		sleep(10)

#=====================================================
#waiting to read command
#=====================================================
def receiveCommand(ser):
	i = 0
	while True:
		#print 'receive:'
		data = ser.read(300)
		if(len(data)>0):
			commandAnalyzer = CommandAnalyzer(data)
			if (commandAnalyzer.getCommandName() == 'gcounters'):
				print 'send reset:',ser.write(PCNCommand.RESET)
				print	'Got data:'
				stopActivities.add(data)
				if(i%10 ==0):
					if (stopActivities.length !=0):
						csvPassenger = CSVPassenger()
						csvPassenger.write(stopActivities.getStopActivities())
						stopActivities.clear()
						print 'write to file:',i
						
						
					print 'reach:',i
				
				i = i + 1

		sleep(0.5)

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
    

    print '\n'
    print 'Version 1 basic reading and writing'
    print '================================='
    print 'port:', ser.port
    print 'baudrate:', ser.baudrate
    print 'bytesize:', ser.bytesize
    print 'parity:', ser.parity
    print 'xonxoff:', ser.xonxoff
    print 'rtscts:', ser.rtscts
    print 'dsrdtr:', ser.dsrdtr
    print '=================================\n'

    ser.flushInput()
    ser.flushOutput()
    
    #=====================================================
    #create thread for sending and receiving command
    #=====================================================
    Thread(target=receiveCommand, args=(ser,)).start()
    Thread(target=sendCommand, args=(ser,)).start()

    while 1:
	
	sleep(10)
        
