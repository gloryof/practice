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

	return createMiddleware(tc.serviceName, conf.pod), nil
}

func createMiddleware(serviceName string, podConfig podConfig) echo.MiddlewareFunc {
	return func(next echo.HandlerFunc) echo.HandlerFunc {
		return func(c echo.Context) error {
			sp := opentracing.StartSpan(
				serviceName,
				opentracing.Tag{Key: "span.kind", Value: "SERVER"},
				opentracing.Tag{Key: "custom.k8s.nameSpace", Value: podConfig.nameSpace},
				opentracing.Tag{Key: "custom.k8s.nodeName", Value: podConfig.nodeName},
				opentracing.Tag{Key: "custom.k8s.podIp", Value: podConfig.podIP},
				opentracing.Tag{Key: "custom.k8s.podName", Value: podConfig.podName},
			)
			opentracing.GlobalTracer().Inject(
				sp.Context(),
				opentracing.HTTPHeaders,
				opentracing.HTTPHeadersCarrier(c.Request().Header))
			defer sp.Finish()
			result := next(c)
			return result
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

type remoteEndpoint struct {
	ipv4 string
	port int
}
