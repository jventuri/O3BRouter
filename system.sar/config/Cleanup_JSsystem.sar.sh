#!/bin/sh

# This script is a place-holder where you can specify custom clean up action that you want to carry out
# when you uninstall your application
# Junos Space Platform will automatically invoke this script during system.sar uninstall operation

echo "custom clean up called for system.sar " >> /tmp/error.log

exit 0
