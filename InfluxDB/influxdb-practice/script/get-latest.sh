#!/bin/zsh

MINUTE=15
if [ $# != 0 ]; then
  MINUTE=$1
fi

curl  http://localhost:8080/api/io-stats/latest/${MINUTE}
