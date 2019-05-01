FROM debian:stretch-slim

ENV NODE_EXPORTER_VERSION=0.17.0

RUN apt-get update && apt-get -y install curl

RUN curl -O -fSL "https://github.com/prometheus/node_exporter/releases/download/v${NODE_EXPORTER_VERSION}/node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz" \
    && mkdir /node_exporter \
    && tar -xf node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz -C /node_exporter --strip-components=1 --no-same-owner \
    && rm node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz

ENV PATH /node_exporter:$PATH

COPY build/go-front /go-front
COPY conf.json /conf.json

COPY start.sh /start.sh
RUN chmod 755 /start.sh

EXPOSE 8000 9100

ENTRYPOINT [ "/bin/sh", "/start.sh"  ]