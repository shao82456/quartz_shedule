#!/usr/bin/env bash
#启动remote
cd $(pwd)/..

workhome=$(pwd)
JAVA=$JAVA_HOME/bin/java
mvn clean compile assembly:single
# Set the name and location of the quartz.properties file
QUARTZ_PROPS="-Dorg.quartz.properties=client.properties"
$JAVA -cp target/quartz_schedule-1.0-jar-with-dependencies.jar $QUARTZ_PROPS Client job11 triger11 "echo ss" 100
#启动worker
