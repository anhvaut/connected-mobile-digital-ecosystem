#!/bin/bash
while true; do
	ipaddrs=`arp -vn|grep wlan0|awk '{print  $1}'`
	iplist=($ipaddrs)
	for i in $(seq 0 $((${#iplist[*]} - 1))); do
		pingresult=`ping -c 2 ${iplist[$i]}|grep ttl`
		if [ ! -n "$pingresult" ] ; then
			deleteip=`arp -d ${iplist[$i]}`
		fi
	done
	sleep 10
done
