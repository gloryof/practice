#!/bin/zsh

COUNT=1
if [ $# != 0 ]; then
  COUNT=$1
fi

iostat -c ${COUNT} | awk -v OFS=, 'NR>1{print $1,$2,$3,$4,$5,$6,$7,$8,$9}' > ./generate-data/result.csv