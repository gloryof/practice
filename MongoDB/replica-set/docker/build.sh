#!/bin/sh

yes | docker-compose rm;
openssl rand -base64 512 > ./db/keyfile/key
docker-compose build;