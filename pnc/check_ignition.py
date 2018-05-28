#!/usr/bin/env python

import sys,time,os
import portio

if os.getuid():
    print 'you need to be root! Exiting.'
    sys.exit()

status = portio.ioperm(0xEE0,1,1)
if status:
    print 'ioperm:',os.strerror(status)
    sys.exit()
while 1:
    ignition = portio.inb(0x0EE0) & 4

    if ignition == 4 :
        print 'ignition ok', ignition
        time.sleep(60)
    else:
        os.system("shutdown now -h")
