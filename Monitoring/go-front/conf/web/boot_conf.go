package web

import (
	"encoding/json"
	"errors"
	"io/ioutil"
	"net/url"
)

// BootConfig 起動設定
type BootConfig struct {
	// Proxy プロキシ先の設定
	Proxy ProxyConfig
}

// ProxyConfig プロキシ設定
type ProxyConfig struct {
	// Pass プロキシ先のパス
	Pass url.URL
}

// JSONConfig JSONの中身
type JSONConfig struct {
	// ProxyPass プロキシ先のパス
	ProxyPass string
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

// convertConfig JSON設定から起動設定に変換する
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
