
import binascii

class CommandAnalyzer:

#========================================================================
#Constructor
#========================================================================
	def __init__ (self,command):
		try:
			self.command = command
			self.hexaCommand = binascii.b2a_hex(command)
			if(self.getCommandLength()>=43):
				self.numberPassengerOn = 0
				self.numberPassengerOff = 0
				self.returnValue = self.getReturnValue()
		except:
			print 'error:'
			#print 'hexa command:', self.hexaCommand
			#print 'command:', binascii.a2b_hex(self.hexaCommand)

#========================================================================
#Hexa to dec
#========================================================================
	def hexToDec(self,s):
		return int(s,16)

#========================================================================
#Get source of package
#========================================================================
	def getSource(self):
		source = self.hexaCommand[12] + self.hexaCommand[13]
		return self.hexToDec(source)

#========================================================================
#Get destination of package
#========================================================================
	def getDestination(self):
		destination = self.hexaCommand[14] + self.hexaCommand[15]
		return self.hexToDec(destination)
	
#========================================================================
#Get data length
#========================================================================
	def getDataLength(self):
		len = self.hexaCommand[20] + self.hexaCommand[21]
		return self.hexToDec(len)
		
#========================================================================
#Get command length
#========================================================================
	def getCommandLength(self):
		return len(self.hexaCommand)

#========================================================================
#Get command length
#========================================================================
	def getPassengers(self,value):
		passengerOn = '0'
		passengerOff = '0'
		
		if (value !=''):
			for i in range(0,7):
				if(i!=0 and value[i]=='0'):
					break
				passengerOn += str(value[i])
				
			for i in range(8,15):
				if(i!=8 and value[i]=='0'):
					break
				passengerOff += str(value[i])
		
		self.numberPassengerOn = self.hexToDec(passengerOn)
		self.numberPassengerOff = self.hexToDec(passengerOff)
		
		return str(self.numberPassengerOn),',',str(self.numberPassengerOff)
		
#========================================================================
#Get command hexa name
#========================================================================
	def getCommandNameHexa(self):
		commandName = ''
		if(self.getCommandLength()>=43):
			len = 24 + 2*self.getDataLength()

			for i in range(24,len):

				if(self.hexaCommand[i] + self.hexaCommand[i+1] =='00'):
					break

				commandName += self.hexaCommand[i]
			
		return commandName

		
#========================================================================
#Get return value
#========================================================================
	def getReturnValue(self):
		commandNameHexa = self.getCommandNameHexa()
		start = 26 + len(commandNameHexa)
		end = self.getCommandLength() - 6
		
		if(end>start):
		
			value = self.hexaCommand[start:end]
			if (binascii.a2b_hex(commandNameHexa) == 'gdoorstatus'):
				self.doorStatus = self.hexToDec(value)
				return self.doorStatus
				
			elif (binascii.a2b_hex(commandNameHexa) == 'gcounters'):
				return self.getPassengers(value)
			
			else: 
				return binascii.a2b_hex(value)
			
		return ''
	
#========================================================================
#Get command name
#========================================================================
	def getCommandName(self):
		return binascii.a2b_hex(self.getCommandNameHexa())
		
#========================================================================
#Get number passenger on
#========================================================================
	def getNumberPassengerOn(self):
		return self.numberPassengerOn

#========================================================================
#Get number passenger off
#========================================================================
	def getNumberPassengerOff(self):
		return self.numberPassengerOff
		
#========================================================================
#Get doorstatus
#========================================================================
	def getDoorStatus(self):
		return self.doorStatus
		
#========================================================================
#Print command info
#========================================================================
	def printCommandInfo(self):
		print '\n'
		print '============= Command Info =================='
		print 'Hexa Command:', self.hexaCommand
		print 'Len:', self.getCommandLength()
		print 'From source:',self.getSource()
		print 'To destination:',self.getDestination()
		print 'Command Name:', self.getCommandName()
		print 'Value:', self.returnValue

