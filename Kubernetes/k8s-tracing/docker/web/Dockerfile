FROM opentracing/nginx-opentracing:0.9.0

RUN mkdir /etc/nginx-zipkin

CMD /bin/sh -c "envsubst < /etc/nginx/templates/nginx-env.conf.template > /etc/nginx/conf.d/nginx-env.conf && nginx -g 'daemon off;'"