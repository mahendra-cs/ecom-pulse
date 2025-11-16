#!/bin/bash
ps aux | grep jmeter | grep -v grep | awk '{print $2}' | xargs kill -9
jmeter -J-XX:-UseContainerSupport -n -t /jmeter/create_order.jmx -l /jmeter/results/results.jtl
