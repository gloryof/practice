FROM centos:8.1.1911

RUN  yum -y install http://www.pgpool.net/yum/rpms/4.0/redhat/rhel-7-x86_64/pgpool-II-release-4.0-1.noarch.rpm
RUN  yum -y install pgpool-II-pg11

RUN mkdir /config
COPY /config/*.conf /config/
COPY /script/start-up.sh /start-up.sh

EXPOSE 9898
EXPOSE 5432

CMD ["/start-up.sh"]