package web

import (
	"encoding/json"
	"errors"
	"io/ioutil"
	"net/url"

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

	prx := []*middleware.ProxyTarget{
		{
			URL: &(c.Proxy.Pass),
		},
	}
	e.Use(middleware.Proxy(middleware.NewRoundRobinBalancer(prx)))

	return e, nil
}

// StartServer Webのコンテナの機動を行う
func StartServer(server *echo.Echo) {

	server.Start(":8000")
}

// loadConfig 設定をロードする
func loadConfig(path string) (BootConfig, error) {

	jc, err := loadFromPath(path)

	if err != nil {

		return BootConfig{}, err
	}

	b, ve := convertConfig(jc)

	if ve != nil {

		return BootConfig{}, ve
	}

	return b, nil
}

func loadFromPath(path string) (JSONConfig, error) {

	jc := JSONConfig{
		ProxyPass: "http://localhost:8080/",
	}

	if len(path) < 1 {

		return jc, nil
	}

	bs, err := ioutil.ReadFile(path)

	if err != nil {

		return JSONConfig{}, err
	}

	ue := json.Unmarshal(bs, &jc)

	if ue != nil {

		return JSONConfig{}, ue
	}

	return jc, nil
}

func convertConfig(jsonConfig JSONConfig) (BootConfig, error) {

	spr := jsonConfig.ProxyPass

	if len(spr) < 1 {
		return BootConfig{}, errors.New("ProxyPassの値が未設定です")
	}

	config := BootConfig{}

	var pr *url.URL
	var er error
	if pr, er = url.Parse(spr); er != nil {

		return BootConfig{}, errors.New("ProxyPassの値が不正です[URL" + spr + "]")
	}

	config.Proxy.Pass = *pr

	return config, nil
}
