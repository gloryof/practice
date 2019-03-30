FROM httpd:2.4.38


ENV NODE_EXPORTER_VERSION=0.17.0
ENV APACHE_EXPORTER_VERSION=0.5.0

RUN apt-get update && apt-get -y install curl

RUN curl -O -fSL "https://github.com/prometheus/node_exporter/releases/download/v${NODE_EXPORTER_VERSION}/node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz" \
    && mkdir /node_exporter \
    && tar -xf node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz -C /node_exporter --strip-components=1 --no-same-owner \
    && rm node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz

RUN curl -O -fSL "https://github.com/Lusitaniae/apache_exporter/releases/download/v${APACHE_EXPORTER_VERSION}/apache_exporter-${APACHE_EXPORTER_VERSION}.linux-amd64.tar.gz" \
    && mkdir /apache_exporter \
    && tar -xf apache_exporter-${APACHE_EXPORTER_VERSION}.linux-amd64.tar.gz -C /apache_exporter --strip-components=1 --no-same-owner \
    && rm apache_exporter-${APACHE_EXPORTER_VERSION}.linux-amd64.tar.gz

COPY httpd.conf /usr/local/apache2/conf/httpd.conf
COPY start.sh /usr/local/apache2/start.sh
RUN chmod 755 /usr/local/apache2/start.sh

ENV PATH /node_exporter:/apache_exporter:$PATH

EXPOSE 80 9117

ENTRYPOINT [ "/usr/local/apache2/start.sh" ]