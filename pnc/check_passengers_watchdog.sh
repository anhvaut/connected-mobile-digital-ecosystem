#!/bin/bash
ppp0_ip=`/sbin/ifconfig ppp0 |grep "inet addr"`
while [ -n = "$ppp0_ip" ] ; do
	sleep 10
	ppp0_ip=`/sbin/ifconfig ppp0 |grep "inet addr"`
done

check_gps=`/bin/netstat -an |grep 2947| grep FIN_WAIT`
if [ -n "$check_gps" ] ; then
    ps -eaf|grep check_passengers_thread.py|grep -v grep| awk -F " " '{system("kill -9 "$2)}'
	sleep 20
fi

if [ -e "/tmp/remote_command.log" ] ; then
        echo `date` "cat remote_command.log"
        echo `/bin/cat /tmp/remote_command.log`
	check_remote=`/bin/cat /tmp/remote_command.log`
    rm -f /tmp/remote_command.log
    restart_check=`echo $check_remote | grep "restart"`
    if [ -n "$restart_check" ] ; then
    	echo `date` "restart check_passenger_thread"
    	ps -eaf|grep check_passengers_thread.py|grep -v grep| awk -F " " '{system("kill -9 "$2)}'
		sleep 20
	fi
    reboot_check=`echo $check_remote | grep "reboot"`
	if [ -n "$reboot_check" ] ; then
                echo `date` "reboot machine"
		shutdown -r now
		echo "reboot_match"
	fi
    email_check=`echo $check_remote | grep "email"`
	if [ -n "$email_check" ] ; then
		echo `date` "email requested"
		/etc/init.d/email_update.py
	fi
fi
last_upload=`tail -100 /var/log/checkpassenger.log|grep "Normal Upload of bus Location ("|tail -1`
last_upload_time=`echo $last_upload|awk -F" " '{print $1" "$2}'|awk -F"," '{print $1}'`
time_check=""
echo `date` "checkpassenger.log: " $last_upload
if [ ! -n "$last_upload" ] ; then
	last_timeentry=`/usr/bin/tail -200 /var/log/checkpassenger.log|grep "^[0-9]\{4\}-[0-9]\{2\}"|grep "__Main__"|tail -1`
	last_timestamp=`echo $last_timeentry|awk -F" " '{print $1" "$2}'|awk -F"," '{print $1}'`
	time_check=`/etc/init.d/check_upload_time.py "$last_timestamp"`
else
	time_check=`/etc/init.d/check_upload_time.py "$last_upload_time"`
fi

restart_check=`echo $time_check|grep restart`
if [ -n "$restart_check" ] ; then
    	echo `date` "last upload time is 2 minutes earlier restart process"
    	ps -eaf|grep check_passengers_thread.py|grep -v grep| awk -F " " '{system("kill -9 "$2)}'
		sleep 20
fi
check_passenger=`/bin/ps -eaf|grep "check_passengers_thread.py"|grep python`
if [ ! -n "$check_passenger" ] ; then
        echo `date` "restarting check_paessenger_thread.py" 
	nohup /etc/init.d/check_passengers_thread.py > /dev/null 2>&1 &
fi

check_mac=`/bin/ps -eaf|grep "check_mac.sh"|grep -v grep`
if [ ! -n "$check_mac" ] ; then
	nohup /etc/init.d/check_mac.sh > /dev/null 2>&1 &
fi

