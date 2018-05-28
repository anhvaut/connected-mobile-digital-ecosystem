#!/usr/bin/env python
import sys
import datetime
import MySQLdb as mdb
from threading import Thread
from time import sleep


def syncPassengerData():
	while True:
		
		conn = mdb.connect('127.0.0.1', 'root', '', 'buslocation');
		cursor = conn.cursor()
		cursorUpdate = conn.cursor()
		cursor.execute("select * from buspassengermac where is_synced is null or is_synced = 0")
		rows = cursor.fetchall()

		for row in rows:
			#print str(row[0])
			print "%s %s %s %s %s %s" % (str(row[0]), str(row[1]), str(row[2]), str(row[3]), str(row[4]), str(row[5]))
			#busClient.service.insertpassengerMAC(row[0], row[1], row[2]),row[3],row[4],row[5])
			cursorUpdate.execute("Update buspassengermac set is_synced = 1 where time_stamp = '%s'" % (str(row[3])));
			conn.commit()
			break;

		cursor.close()
		conn.close()

		#print 'done:'
		sleep(2)

Thread(target=syncPassengerData, args=()).start()
