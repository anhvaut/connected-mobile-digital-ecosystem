import datetime
from commandanalyzer import CommandAnalyzer

class StopActivity:

	def setSource(self,source):
		self.source = source

	def setDestination(self,destination):
		self.destination = destination

	def setStatus(self,status):
		self.status = status

	def setDateTime(self,datetime):
		self.datetime = datetime

	def setPassengerOn(self,passengerOn):
		self.passengerOn = passengerOn

	def setPassengerOff(self,passengerOff):
		self.passengerOff = passengerOff


#==================================================================
class StopActivities:

	def __init__ (self):
		self.stopActivities = []
		self.length = 0

	def add(self,command):
		commandAnalyzer = CommandAnalyzer(command)
		commandName = commandAnalyzer.getCommandName()
		

		if(commandName == 'gcounters'):
			commandAnalyzer.printCommandInfo()
			passengerOn = commandAnalyzer.getNumberPassengerOn()
			passengerOff = commandAnalyzer.getNumberPassengerOff()
			
			if(passengerOn !=0 or passengerOff !=0):
				stopActivity = StopActivity()
				stopActivity.setSource(commandAnalyzer.getSource())
				stopActivity.setDestination(commandAnalyzer.getDestination())
				stopActivity.setStatus('')
				stopActivity.setDateTime(str(datetime.datetime.now()))
				stopActivity.setPassengerOn(passengerOn)
				stopActivity.setPassengerOff(passengerOff)
				self.stopActivities.append(stopActivity)
				
				self.length += 1

	def getStopActivities(self):
		return self.stopActivities
		
	
	def clear(self):
		self.stopActivities = []
		self.length = 0
		
	
