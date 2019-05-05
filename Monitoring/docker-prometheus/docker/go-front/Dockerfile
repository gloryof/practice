FROM debian:stretch-slim

COPY build/go-front /go-front
COPY conf.json /conf.json

EXPOSE 8000

ENTRYPOINT [ "/go-front", "-c", "/conf.json"]