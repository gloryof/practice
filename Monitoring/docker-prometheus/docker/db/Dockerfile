FROM postgres:11

ENV POOSTGRES_EXPORTER_VERSION=v0.4.7

RUN apt-get update && apt-get -y install curl

RUN curl -O -fSL "https://github.com/wrouesnel/postgres_exporter/releases/download/${POOSTGRES_EXPORTER_VERSION}/postgres_exporter_${POOSTGRES_EXPORTER_VERSION}_linux-amd64.tar.gz" \
    && mkdir /postgres_exporter \
    && tar -xf postgres_exporter_${POOSTGRES_EXPORTER_VERSION}_linux-amd64.tar.gz -C /postgres_exporter --strip-components=1 --no-same-owner \
    && rm postgres_exporter_${POOSTGRES_EXPORTER_VERSION}_linux-amd64.tar.gz


COPY create-database.sql /docker-entrypoint-initdb.d/create-database.sql
COPY start.sh start.sh
RUN chmod 755 /start.sh

ENV PATH /postgres_exporter:$PATH

EXPOSE 5432 9187

ENTRYPOINT [ "./start.sh" ]