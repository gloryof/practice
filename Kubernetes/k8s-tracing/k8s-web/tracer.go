package main

import (
	"net/http"
	"strconv"

	zipkin "github.com/openzipkin/zipkin-go"
	zipkinmhttp "github.com/openzipkin/zipkin-go/middleware/http"
	zipkinhttp "github.com/openzipkin/zipkin-go/reporter/http"
)

func createTracingMiddleware(conf config) (func(http.Handler) http.Handler, error) {
	bc := conf.boot
	tc := conf.tracing

	reporter := zipkinhttp.NewReporter(tc.createURL())
	noop := func(http.Handler) http.Handler { return nil }

	endpoint, err := zipkin.NewEndpoint(tc.serviceName, "localhost:"+strconv.Itoa(bc.port))
	if err != nil {
		return noop, err
	}

	tracer, err := zipkin.NewTracer(reporter, zipkin.WithLocalEndpoint(endpoint))
	if err != nil {
		return noop, err
	}

	return zipkinmhttp.NewServerMiddleware(
		tracer, zipkinmhttp.TagResponseSize(true),
	), nil
}
