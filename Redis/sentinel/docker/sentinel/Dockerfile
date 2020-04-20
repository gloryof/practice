FROM redis:5.0.8

RUN mkdir /data/conf

COPY /conf/sentinel.conf   /data/conf/sentinel.conf 
CMD [ "redis-server", "/data/conf/sentinel.conf", "--sentinel" ]