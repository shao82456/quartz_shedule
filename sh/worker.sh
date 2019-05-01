#!/usr/bin/env bash
#启动remote
cd $(pwd)/..

workhome=$(pwd)
JAVA=$JAVA_HOME/bin/java
mvn clean compile assembly:single

# Set the name and location of the quartz.properties file
QUARTZ_PROPS2="-Dorg.quartz.properties=quartz.properties"
#$JAVA -cp "jars/*" $QUARTZ_PROPS2 ClusterServer >$workhome/workerlog/server1.log 2>&1
#$JAVA -cp "jars/*" $QUARTZ_PROPS2 ClusterServer >$workhome/workerlog/server2.log 2>&1
#$JAVA -cp "jars/*" $QUARTZ_PROPS2 ClusterServer >$workhome/workerlog/server3.log 2>&1

#启动worker
$JAVA -cp "jars/*" $QUARTZ_PROPS2 ClusterServer
