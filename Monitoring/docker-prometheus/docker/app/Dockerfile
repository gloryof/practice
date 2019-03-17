FROM openjdk:11

ENV NODE_EXPORTER_VERSION=0.17.0

COPY build/monitoring-app.jar monitoring-app.jar

COPY start.sh start.sh
RUN chmod 755 /start.sh

RUN curl -O -fSL "https://github.com/prometheus/node_exporter/releases/download/v${NODE_EXPORTER_VERSION}/node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz" \
    && mkdir /node_exporter \
    && tar -xf node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz -C /node_exporter --strip-components=1 --no-same-owner \
    && rm node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz


ENV PATH /node_exporter:$PATH

EXPOSE 8080 9100

ENTRYPOINT ["/start.sh"]
