FROM httpd:2.4.38


ENV APACHE_EXPORTER_VERSION=0.5.0

RUN apt-get update && apt-get -y install curl

RUN curl -O -fSL "https://github.com/Lusitaniae/apache_exporter/releases/download/v${APACHE_EXPORTER_VERSION}/apache_exporter-${APACHE_EXPORTER_VERSION}.linux-amd64.tar.gz" \
    && mkdir /apache_exporter \
    && tar -xf apache_exporter-${APACHE_EXPORTER_VERSION}.linux-amd64.tar.gz -C /apache_exporter --strip-components=1 --no-same-owner \
    && rm apache_exporter-${APACHE_EXPORTER_VERSION}.linux-amd64.tar.gz

COPY httpd.conf /usr/local/apache2/conf/httpd.conf
COPY start.sh /usr/local/apache2/start.sh
RUN chmod 755 /usr/local/apache2/start.sh

ENV PATH /apache_exporter:$PATH

EXPOSE 80 9117

ENTRYPOINT [ "/usr/local/apache2/start.sh" ]