FROM postgres:11

RUN mkdir /docker-entrypoint-initdb.d/script
COPY ./script/create-database.sql /docker-entrypoint-initdb.d/create-database.sql
