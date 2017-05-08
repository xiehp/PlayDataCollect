#!/bin/bash

APP_LIB1=/usr/local/tomcat/applib
APP_LIB2=/usr/local/tomcat/webapps/ROOT/WEB-INF/lib
APP_HOME=/usr/local/tomcat/webapps/ROOT/WEB-INF/lib
APP_CLASSPATH=$APP_HOME/bin

jarList1=$(ls $APP_LIB1|grep jar)
jarList2=$(ls $APP_LIB2|grep jar)
echo jarList1: $jarList1
echo
echo
echo jarList2: $jarList2
echo
echo


for i in $jarList1
do
 APP_CLASSPATH="$APP_LIB1/$i":$APP_CLASSPATH
done
echo APP_CLASSPATH: $APP_CLASSPATH
echo
echo

for i in $jarList2
do
 APP_CLASSPATH="$APP_LIB2/$i":$APP_CLASSPATH
done
echo APP_CLASSPATH: $APP_CLASSPATH
echo
echo

export CLASSPATH=$CLASSPATH:$APP_CLASSPATH
echo CLASSPATH: $CLASSPATH
echo
echo

java -Xms50m -Xmx250m -Dhttp.proxyHost=10.144.224.190 -Dhttp.proxyPort=7888 -Dhttps.proxyHost=10.144.224.190 -Dhttps.proxyPort=7888 xie.animeshotsite.utils.ResourceCollectUtils

echo Process End

exit