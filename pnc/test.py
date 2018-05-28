#!/usr/bin/env python
import serial, sys, binascii, csv
from threading import Thread
from time import sleep
from ctypes import string_at
from sys import getsizeof
from binascii import hexlify

from pcncommand import PCNCommand
from commandanalyzer import CommandAnalyzer
from stopactivity import StopActivities
from csvpassenger import CSVPassenger

ser='Serial port'

command = PCNCommand.GET_COUNTERS
#pcnCommand = PCNCommand()
#command = pcnCommand.getCommand('gcounters')

commandAnalyzer = CommandAnalyzer(command)
commandAnalyzer.printCommandInfo()

stopActivities = StopActivities()

#print 'len',len(stopActivities.getStopActivities())
#=====================================================
#send command to pcn
#=====================================================
def sendCommand(ser):
	while True:
		#print 'send:',ser
		sleep(0.5)

#=====================================================
#waiting to read command
#=====================================================
def receiveCommand(ser):
	i = 0
	while True:
		#print 'receive:',ser
		
		stopActivities.add(command)
		i = i + 1

		if(i==10):		
			csvPassenger = CSVPassenger()
			csvPassenger.write(stopActivities.getStopActivities())

			for s in stopActivities.getStopActivities():
				print 'source=',s.source,' @destination =', s.destination
			print 'i=',i
			

		sleep(0.5)


#=====================================================
#create thread for sending and receiving command
#=====================================================
Thread(target=sendCommand, args=(ser,)).start()
Thread(target=receiveCommand, args=(ser,)).start()




