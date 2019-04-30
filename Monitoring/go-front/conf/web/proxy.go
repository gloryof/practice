package web

import (
	"strings"

	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
)

// createProxyConfig ProxyConfigを作成する
func createProxyConfig(config BootConfig) middleware.ProxyConfig {

	return middleware.ProxyConfig{
		Skipper:  createSkipper(),
		Balancer: createProxyBlancer(config.Proxy),
		Rewrite:  createRewriteMap(),
	}
}

// createSkipper Skipperを作成する
func createSkipper() middleware.Skipper {

	return func(e echo.Context) bool {

		isProxy := strings.HasPrefix(e.Path(), "/api")
		return !(isProxy)
	}
}

// createProxyBlancer ProxyBalancerを作成する
func createProxyBlancer(proxyConfig ProxyConfig) middleware.ProxyBalancer {

	prx := []*middleware.ProxyTarget{
		{
			URL: &(proxyConfig.Pass),
		},
	}

	return middleware.NewRoundRobinBalancer(prx)
}

// createRewriteMap RewriteMapを作成する
func createRewriteMap() map[string]string {

	m := make(map[string]string)

	m["/api*"] = "/$1"

	return m
}
