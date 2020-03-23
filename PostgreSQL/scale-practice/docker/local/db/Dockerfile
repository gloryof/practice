FROM postgres:11

RUN mkdir /docker-entrypoint-initdb.d/script
COPY ./script/create-database.sql /docker-entrypoint-initdb.d/script/create-database.sql
COPY ./script/data.sql /docker-entrypoint-initdb.d/script/data.sql
COPY init.sh /docker-entrypoint-initdb.d/init.sh