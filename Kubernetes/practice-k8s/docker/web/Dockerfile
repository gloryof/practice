FROM nginx:1.17.10

RUN mkdir /pages

COPY /html/index.html /usr/share/nginx/html/index.html
COPY /conf/nginx.conf /etc/nginx/nginx.conf

USER nginx

EXPOSE 3000
