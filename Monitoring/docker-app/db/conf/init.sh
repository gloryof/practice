#!/bin/sh
set -e

INIT_FLG_FILE=${PGDATA}/execute-init

if [ ! -e ${INIT_FLG_FILE} ]; then

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER "monitor-user"
      WITH PASSWORD 'monitor-pass';
    CREATE DATABASE "monitor-db"
      WITH OWNER = "monitor-user"
        ENCODING = "UTF-8";
    GRANT ALL PRIVILEGES ON DATABASE "monitor-db" TO "monitor-user";
EOSQL
else
echo 'init file is exists.'
fi

touch ${INIT_FLG_FILE}
