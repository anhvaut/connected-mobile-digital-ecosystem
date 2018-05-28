
import csv, datetime
from commandanalyzer import CommandAnalyzer

class CSVPassenger:


#===================================================================
#Write
#===================================================================
	def write(self,stopActivities):
		#if not stopActivities:
		filename = 'log/pcn'+ str(datetime.datetime.now()) +'.csv'
		writer = csv.writer(open(filename,'wb'), 
								delimiter=',', quotechar='|', 
								quoting=csv.QUOTE_MINIMAL)

		for s in stopActivities:
				writer.writerow([s.source,s.destination,s.status,s.datetime,s.passengerOn,s.passengerOff])
		

#===================================================================
#Reader
#===================================================================
	def read(self):
		with open('pcn.csv','rb') as f:
			reader = csv.reader(f)
			for row in reader:
				print row
