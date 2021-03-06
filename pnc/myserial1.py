#!/usr/bin/env python
import serial, sys, binascii
from threading import Thread
from time import sleep
from ctypes import string_at
from sys import getsizeof
from binascii import hexlify

from pcncommand import PCNCommand
from commandanalyzer import CommandAnalyzer
from stopactivity import StopActivities
from csvpassenger import CSVPassenger

import sys
import datetime
import MySQLdb as mdb

ser = serial.Serial('/dev/ttyUSB0', 115200, timeout=0)

#command = PCNCommand.GET_COUNTERS
stopActivities = StopActivities()

#print PCNCommand.GET_COUNTERS

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
#send command to pcn
#=====================================================
def sendCommand(ser):
	while True:
		#print 'send:'
		print 'send gcounters:',ser.write(PCNCommand.GET_COUNTERS)
		sleep(2)

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
                                #print 'Got data'		    	
				print 'send reset:',ser.write(PCNCommand.RESET)
				#print	'Got data:'
				#commandAnalyzer.printCommandInfo()
				passengerOn = commandAnalyzer.getNumberPassengerOn()
				passengerOff = commandAnalyzer.getNumberPassengerOff()
				tmpSt = "passenger on: %s , passenger off: %s" % (str(passengerOn), str(passengerOff))
				print tmpSt
				if(passengerOn !=0):
					#update data to local database
                                        writePassengerDataToDB('nan','nan',1,str(passengerOn))

				if(passengerOff !=0):
					#update data to local database
                                        writePassengerDataToDB('nan','nan',0,str(passengerOff))

                        		

		sleep(0.5)

#=================================
# Write passenger on/off to local DB
#=================================
def writePassengerDataToDB(longitude, latitude, on, quantity):
	sqlInsert="INSERT INTO buspassengermac(bus_id,longitude,latitude,time_stamp,on_off,MACAddress) VALUES ('%s','%s','%s','%s','%s','%s')"

	conn = mdb.connect('127.0.0.1', 'root', '', 'buslocation')
	cursor = conn.cursor()
	cursor.execute(sqlInsert % ('bus1', longitude,latitude,(datetime.datetime.now()),on,quantity ))
	conn.commit()
	cursor.close()
	conn.close()

#=====================================================
#create thread for sending and receiving command
#=====================================================
Thread(target=receiveCommand, args=(ser,)).start()
Thread(target=sendCommand, args=(ser,)).start()

