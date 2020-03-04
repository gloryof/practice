FROM postgres:11

ARG profile

ENV DB_NAME=${profile}-database
ENV DB_USER=${profile}-user
ENV DB_PASSWORDD=${profile}-password

COPY ./script/create-database-${profile}.sql /docker-entrypoint-initdb.d/script/create-database.sql
COPY ./script/data-${profile}.sql /docker-entrypoint-initdb.d/script/data.sql
COPY init.sh /docker-entrypoint-initdb.d/init.sh