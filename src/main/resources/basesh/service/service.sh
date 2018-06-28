#!/bin/sh
#service jar dir
cd /usr/local/apollo/ITBus-client
#start command
nohup java -jar client-base-ITBus.jar >/dev/null 2>&1 & 
pid=`ps -ef|grep client-base-ITBus.jar |grep -v grep |awk '{print $2}'`
echo "$pid" > pid.pid

