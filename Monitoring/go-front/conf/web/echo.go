package web

import (
	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
)

// CreateWebContainer Webのコンテナを作成する
func CreateWebContainer(path string) (*echo.Echo, error) {

	c, err := loadConfig(path)

	if err != nil {

		return nil, err
	}

	e := echo.New()

	e.Use(middleware.ProxyWithConfig(createProxyConfig(c)))

	return e, nil
}

// StartServer Webのコンテナの機動を行う
func StartServer(server *echo.Echo) {

	server.Start(":8000")
}
