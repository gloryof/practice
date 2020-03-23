#!/bin/sh

set -e

INIT_FLG_FILE=${PGDATA}/execute-init

if [ ! -e ${INIT_FLG_FILE} ]; then

echo "start create database"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -f /docker-entrypoint-initdb.d/script/create-database.sql

if [ $? != 0 ]; then
echo "[FATAL] Creating database is failed"
return 1
fi
echo "Creating database is success"

echo "start insert data"

PGPASSWORD=local-password psql -v ON_ERROR_STOP=1 --username "local-user" local-database -f /docker-entrypoint-initdb.d/script/data.sql

if [ $? != 0 ]; then
echo "[FATAL] Inserting data is failed"
return 1
fi
echo "Inserting data is success"

else
echo 'init file is exists.'
fi

touch ${INIT_FLG_FILE}