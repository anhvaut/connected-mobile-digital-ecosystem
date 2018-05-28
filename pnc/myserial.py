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

ser = serial.Serial('/dev/ttyUSB0', 115200, timeout=0)

#command = PCNCommand.GET_COUNTERS
stopActivities = StopActivities()

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


#=====================================================
#create thread for sending and receiving command
#=====================================================
Thread(target=receiveCommand, args=(ser,)).start()
Thread(target=sendCommand, args=(ser,)).start()

