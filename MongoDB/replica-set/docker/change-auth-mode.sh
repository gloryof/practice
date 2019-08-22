#!/bin/sh

docker-compose stop;

cp ./conf/mongod-auth-mode.conf ./conf/mongod.conf;

docker-compose up -d;