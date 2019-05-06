#!/usr/bin/env bash
#启动remote
cd $(pwd)/..

workhome=$(pwd)
JAVA=$JAVA_HOME/bin/java
mvn clean compile assembly:single

# Set the name and location of the worker.properties file
QUARTZ_PROPS2="-Dorg.quartz.properties=worker.properties"
#$JAVA -cp "jars/*" $QUARTZ_PROPS2 shao.ClusterServer >$workhome/workerlog/server1.log 2>&1
#$JAVA -cp "jars/*" $QUARTZ_PROPS2 shao.ClusterServer >$workhome/workerlog/server2.log 2>&1
#$JAVA -cp "jars/*" $QUARTZ_PROPS2 shao.ClusterServer >$workhome/workerlog/server3.log 2>&1

#启动worker
$JAVA -cp "jars/*" $QUARTZ_PROPS2 shao.ClusterServer


#QUARTZ_PROPS2="-Dorg.quartz.properties=worker.properties"
#java -cp "jars/*" $QUARTZ_PROPS2 shao.ClusterServer
