#!/bin/sh

TARGETS="metabase"
START_COMMAND="start"
STOP_COMMAND="stop"

if [ $# -ne 1 ]; then
    echo "Please input start or stop"
    exit 1
fi

if [ $1 != ${START_COMMAND} -a $1 != ${STOP_COMMAND} ]; then
    echo "Error"
    exit 1
fi

if [ $1 = ${START_COMMAND} ]; then
    echo "====== ${START_COMMAND} ${TARGETS} ======="
    docker-compose up -d ${TARGETS}
fi

if [ $1 = ${STOP_COMMAND} ]; then
    echo "====== ${STOP_COMMAND} ${TARGETS} ======="
    docker-compose stop ${TARGETS}
fi