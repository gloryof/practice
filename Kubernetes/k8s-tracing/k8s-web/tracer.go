package main

import (
	"log"
	"net/http"
	"os"

	zipkin "github.com/openzipkin/zipkin-go"
	zipkinhttp "github.com/openzipkin/zipkin-go/middleware/http"
	logreporter "github.com/openzipkin/zipkin-go/reporter/log"
)

func createTracingMiddleware(conf tracingConfig) (func(http.Handler) http.Handler, error) {

	reporter := logreporter.NewReporter(log.New(os.Stderr, "", log.LstdFlags))
	noop := func(http.Handler) http.Handler { return nil }

	endpoint, err := zipkin.NewEndpoint(conf.serviceName, conf.createURL())
	if err != nil {
		return noop, err
	}

	tracer, err := zipkin.NewTracer(reporter, zipkin.WithLocalEndpoint(endpoint))
	if err != nil {
		return noop, err
	}

	return zipkinhttp.NewServerMiddleware(
		tracer, zipkinhttp.TagResponseSize(true),
	), nil
}
