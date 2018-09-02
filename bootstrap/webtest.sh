#!/bin/bash

umask 000
set -e

source /bootstrap/utils.sh

app=${BAMBOO_URL}/bamboo

waitForApp $app
sleep 10

cd /opt/test && ./gradlew -Dgeb.env=chromeHeadless --info test
