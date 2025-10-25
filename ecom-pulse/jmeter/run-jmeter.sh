#!/bin/bash
jmeter -J-XX:-UseContainerSupport -n -t /jmeter/create_order.jmx -l /jmeter/results/results.jtl
