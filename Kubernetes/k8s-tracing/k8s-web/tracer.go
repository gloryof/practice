package main

import (
	"strconv"

	"github.com/labstack/echo/v4"
	"github.com/opentracing/opentracing-go"
	zipkinot "github.com/openzipkin-contrib/zipkin-go-opentracing"
	zipkin "github.com/openzipkin/zipkin-go"
	zipkinhttp "github.com/openzipkin/zipkin-go/reporter/http"
)

func createTracingMiddleware(conf config) (echo.MiddlewareFunc, error) {
	bc := conf.boot
	tc := conf.tracing

	reporter := zipkinhttp.NewReporter(tc.createURL())
	noop := noop()

	endpoint, err := zipkin.NewEndpoint(tc.serviceName, "localhost:"+strconv.Itoa(bc.port))
	if err != nil {
		return noop, err
	}

	tr, err := zipkin.NewTracer(reporter, zipkin.WithLocalEndpoint(endpoint))
	if err != nil {
		return noop, err
	}

	tracer := zipkinot.Wrap(tr)
	opentracing.SetGlobalTracer(tracer)

	return createMiddleware(tc.serviceName), nil
}

func createMiddleware(serviceName string) echo.MiddlewareFunc {
	return func(next echo.HandlerFunc) echo.HandlerFunc {
		return func(c echo.Context) error {
			sp := opentracing.StartSpan(serviceName)
			opentracing.GlobalTracer().Inject(
				sp.Context(),
				opentracing.HTTPHeaders,
				opentracing.HTTPHeadersCarrier(c.Request().Header))
			defer sp.Finish()
			return next(c)
		}
	}
}

func noop() echo.MiddlewareFunc {
	return func(next echo.HandlerFunc) echo.HandlerFunc {
		return func(c echo.Context) error {
			return next(c)
		}
	}
}
