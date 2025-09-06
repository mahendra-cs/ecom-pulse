#!/bin/bash

# Check if the number of users is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <num_users>"
  exit 1
fi

NUM_USERS=$1

# Set the number of users
curl -X POST -H "Content-Type: application/json" \
-d "{\"num_users\": $NUM_USERS}" \
http://localhost:5001/set-users

# Start the JMeter test
curl -X POST http://localhost:5001/start-jmeter
