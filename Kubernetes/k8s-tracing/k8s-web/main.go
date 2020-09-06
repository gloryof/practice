package main

import (
	"log"
	"net/url"
	"os"
	"strconv"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
)

func main() {
	conf := createConfg()
	e := echo.New()

	tc := conf.tracing
	if tc.enable {
		th, err := createTracingMiddleware(conf)
		if err != nil {
			log.Fatal(err)
		}
		e.Use(th)
	}
	e.Use(middleware.Logger())

	pc, err := conf.proxy.createProxyConfig()
	if err != nil {
		log.Fatal(err)
	}

	e.Use(middleware.ProxyWithConfig(pc))
	e.Logger.Fatal(e.Start(":" + strconv.Itoa(conf.boot.port)))
}

func createConfg() config {
	return config{
		boot: bootConfig{
			port: getIntEnv("BOOT_PORT", 3000),
		},
		tracing: tracingConfig{
			enable:      getBoolEnv("TRACING_ENABLE", false),
			serviceName: getStrEnv("TRACING_SERVIE_NAME", ""),
			host:        getStrEnv("TRACING_HOST", "http://localhost"),
			port:        getIntEnv("TRACING_PORT", 9411),
		},
		proxy: proxyConfig{
			host: getStrEnv("PROXY_HOST", "http://localhost"),
			port: getIntEnv("PROXY_PORT", 8080),
		},
	}
}

type config struct {
	boot    bootConfig
	tracing tracingConfig
	proxy   proxyConfig
	pod     podConfig
}

type bootConfig struct {
	port int
}

type proxyConfig struct {
	host string
	port int
}

type podConfig struct {
	nameSpace string
	nodeName  string
	podIP     string
	podName   string
}

func (p proxyConfig) createURL() string {
	return p.host + ":" + strconv.Itoa(p.port)
}

func (p proxyConfig) createProxyConfig() (middleware.ProxyConfig, error) {
	url, err := url.Parse(p.createURL())
	if err != nil {
		return middleware.ProxyConfig{}, err
	}

	bal := middleware.NewRoundRobinBalancer([]*middleware.ProxyTarget{
		{
			URL: url,
		},
	})
	return middleware.ProxyConfig{
		Balancer: bal,
		Rewrite:  map[string]string{"/api/*": "/api/$1"},
	}, nil
}

type tracingConfig struct {
	enable      bool
	serviceName string
	host        string
	port        int
}

func (t tracingConfig) createURL() string {
	return t.host + ":" + strconv.Itoa(t.port) + "/api/v2/spans"
}

func getBoolEnv(key string, defaultValue bool) bool {
	return "true" == os.Getenv(key)
}

func getIntEnv(key string, defaultValue int) int {
	sv := os.Getenv(key)

	if len(sv) < 1 {
		return defaultValue
	}

	v, err := strconv.Atoi(sv)

	if err != nil {
		return defaultValue
	}

	return v
}

func getStrEnv(key string, defaultValue string) string {
	v := os.Getenv(key)

	if len(v) < 1 {
		return defaultValue
	}

	return v
}
