#!/bin/bash

#/etc/init.d/download_email.py
cd /home/$HOSTNAME/Downloads/emaildownload
dirs=`ls`
echo $dirs
dirlist=($dirs)
for i in $(seq 0 $((${#dirlist[*]} - 1))); do
	echo ${dirlist[$i]}
	ls ${dirlist[$i]}
	${dirlist[$i]}/runcmd.sh
	#rm -rf ${dirlist[$i]}
		
done

