#!/bin/bash

if[$# -lt 3]; then
 echo "Please enter the following parameters as running parameters:"
 echo "<ip>:<port> : IP Address And Server Port"
 echo "<time> : Collection Frequency, Unit is Seconds Or Minutes"
 echo "<dir> :  Result Directory Path"
 echo "Example: 127.0.0.1:9008 30s /home/user"
 
 exit 2
fi

java -jar jvm-monitor.jar $1 $2 $3
  