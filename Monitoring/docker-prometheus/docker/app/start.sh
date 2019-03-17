#!/bin/bash

set -m

java -Djava.security.egd=file:/dev/./urandom -jar /monitoring-app.jar --spring.datasource.url=jdbc:postgresql://mon-pro-db:5432/monitor-db &

node_exporter

fg %1