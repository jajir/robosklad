#!/bin/sh
#
# start Artemis container
#

#
# clean Artemis instance
#
rm -rf artemis-instance
mkdir -p artemis-instance/etc-override
cp broker.xml artemis-instance/etc-override/broker.xml

docker-compose up --detach 

#docker run \
#    --detach \
#    --name mycontainer \
#    --volume broker.xml:/var/lib/artemis-instance/etc-override/broker.xml \
#    -p 61616:61616 -p 8161:8161 \
#    --rm apache/activemq-artemis:latest-alpine
