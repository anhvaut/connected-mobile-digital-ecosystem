#!/usr/bin/env python
import sys
import datetime
import MySQLdb as mdb


class DBHandler:
	
	sqlInsert="INSERT INTO buspassengermac(bus_id,longitude,latitude,time_stamp,on_off,MACAddress) VALUES ('%s','%s','%s','%s','%s','%s')"

	conn = mdb.connect('127.0.0.1', 'root', '', 'buslocation')
	cursor = conn.cursor()
	cursor.execute(sqlInsert % ('bus1', 'nan','nan',(datetime.datetime.now()),'1','2'))
	conn.commit()
	cursor.close()
	conn.close()
