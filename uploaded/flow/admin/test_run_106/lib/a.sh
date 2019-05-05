#!/bin/bash
for ndays in $(seq 1 5)
do
	echo $ndays
	sc=$[$RANDOM%5+1]
	echo $sc
    sleep $[$RANDOM%5+1]s
done
