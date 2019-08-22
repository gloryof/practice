#!/bin/sh

docker-compose stop;

cp ./conf/mongod-noauth-mode.conf ./conf/mongod.conf;

docker-compose up -d;