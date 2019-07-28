FROM mongo:4.0.10

COPY ./conf/mongod.conf /etc/mongo/mongod.conf
COPY entrypoint/01_init-db.js /docker-entrypoint-initdb.d/01_init-db.js