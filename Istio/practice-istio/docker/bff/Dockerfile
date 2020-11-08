FROM golang:1.15.0-alpine AS build-stage

WORKDIR /app

RUN apk add git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Istio/practice-istio/istio-practice-bff' > .git/info/sparse-checkout \
    && git checkout

WORKDIR /app/practice/Istio/practice-istio/istio-practice-bff
RUN CGO_ENABLED=0 go build

FROM busybox:1.32.0-glibc AS exec-stage

COPY --from=build-stage \
    /app/practice/Istio/practice-istio/istio-practice-bff/istio-practice-bff \
    /istio-practice-bff

EXPOSE 3000

ENTRYPOINT ["/istio-practice-bf"]