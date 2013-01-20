#!/bin/bash

CLASSPATH="$CLASSPATH:/home/maryl/NetBeansProjects/jade/lib:/home/maryl/NetBeansProjects/jade-weather-forecast/dist/JadeWeatherForecast.jar"
AGENTS_AMOUNT=5
REGIONS_AMOUNT=2
COUNTER=1

while [[ $COUNTER -le $AGENTS_AMOUNT ]] ; do
	REGION=$(($COUNTER%$REGIONS_AMOUNT))
	java -cp $CLASSPATH jade.Boot -container -container-name station_$COUNTER "station_${COUNTER}_agent:JadeWeatherForecast.Agents.WeatherReporterAgent($REGION,0.0,10.0)" & 
	COUNTER=$((COUNTER+1))
done
