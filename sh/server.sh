#!/usr/bin/env bash
#启动remote
cd $(pwd)/..

workhome=$(pwd)
JAVA=$JAVA_HOME/bin/java
mvn clean compile assembly:single
cp -f target/quartz_schedule-1.0-jar-with-dependencies.jar jars/
# Set the name and location of the worker.properties file
QUARTZ_PROPS1="-Dorg.quartz.properties=remote.properties"
#$JAVA -cp jars/quartz_schedule-1.0-jar-with-dependencies.jar:jars/job.jar  $QUARTZ_PROPS1 shao.RemoteServer clear
$JAVA -cp "jars/*"  $QUARTZ_PROPS1 shao.RemoteServer clear

#启动worker
#./worker.sh
