#! /bin/sh

redis-cli -h ${TARGET_HOST} -p ${TARGET_PORT} INFO > /dev/null


until redis-cli -h ${TARGET_HOST} -p ${TARGET_PORT} INFO > /dev/null;
do
  echo waiting for booting redis;
  sleep 5;
done;

echo complete booting