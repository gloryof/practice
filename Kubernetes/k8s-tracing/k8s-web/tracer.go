package main

import (
	"log"
	"net/http"
	"os"
	"strconv"

	zipkin "github.com/openzipkin/zipkin-go"
	zipkinmhttp "github.com/openzipkin/zipkin-go/middleware/http"
	zipkinhttp "github.com/openzipkin/zipkin-go/reporter/http"
)

func createTracingMiddleware(conf config) (func(http.Handler) http.Handler, error) {
	bc := conf.boot
	tc := conf.tracing

	opt := []zipkinhttp.ReporterOption{
		zipkinhttp.Logger(log.New(os.Stdout, "", log.LstdFlags)),
	}
	reporter := zipkinhttp.NewReporter(tc.createURL(), opt...)
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
