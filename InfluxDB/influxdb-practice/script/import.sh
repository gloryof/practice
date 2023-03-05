#!/bin/zsh

COUNT=1
if [ $# != 0 ]; then
  COUNT=$1
fi

echo "KB/t,tps,MB/s,us,sy,id,1m,5m,15m" > ./generate-data/result.csv
iostat -c "${COUNT}" | awk -v OFS=, 'NR>1{print $1,$2,$3,$4,$5,$6,$7,$8,$9}' | grep "^[0-9]" >> ./generate-data/result.csv