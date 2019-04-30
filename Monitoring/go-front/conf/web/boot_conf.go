package web

import "net/url"

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
