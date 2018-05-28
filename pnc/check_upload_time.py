#!/usr/bin/env python
#

import datetime
import sys

if (len(sys.argv) > 1):
    
    time_stamp = datetime.datetime.now()
#    print sys.argv[1]," ",len(sys.argv[1])
    try :
        log_time = datetime.datetime.strptime(sys.argv[1],"%Y-%m-%d %H:%M:%S")
        if (time_stamp > (log_time+datetime.timedelta(minutes=2))):
            print "restart"
    except:
        print "restart"
