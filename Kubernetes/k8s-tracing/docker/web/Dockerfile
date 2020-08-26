FROM golang:1.15.0-alpine AS build-stage

RUN mkdir /etc/nginx-zipkin

WORKDIR /app

RUN apk add git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Kubernetes/k8s-tracing/k8s-web' > .git/info/sparse-checkout \
    && git checkout

WORKDIR /app/practice/Kubernetes/k8s-tracing/k8s-web
RUN CGO_ENABLED=0 go build

FROM busybox:1.32.0-glibc AS exec-stage

COPY --from=build-stage \
    /app/practice/Kubernetes/k8s-tracing/k8s-web/k8s-web \
    /k8s-web

EXPOSE 3000

ENTRYPOINT ["/k8s-web"]