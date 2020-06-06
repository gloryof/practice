#!/bin/sh

while getopts t:f: OPT
do
  case $OPT in
    "f" ) FROM_FILE=$OPTARG ;;
    "t" ) TO_FILE=$OPTARG ;;
  esac
done

cp ${FROM_FILE} ${TO_FILE}
redis-server ${TO_FILE} --sentinel