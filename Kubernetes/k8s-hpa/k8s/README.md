# Kubernetes
## Metrics server
下記のURLからmanifestをダウンロードする。  
https://github.com/kubernetes-sigs/metrics-server/releases/download/v0.4.1/components.yaml

Deploymentの`spec.template.spec.containers[0].args`に`--kubelet-insecure-tls`を追加する。  
manifestをapplyする。
## Load test
下記のコマンドを実行する。  
戻り値をあとで使用するのでメモをする。
```
$ export TARGET_PORT=3000
$ curl -X POST http://localhost:${TARGET_PORT}/api/person \
    -H "Content-Type: application/json" \
    -d '{"name": "test1", "age": 34}' -v
```

下記のコマンドを実行する。（要：vegeta）
```
$ export TEST_ID=前コマンドの戻り値
$ echo "GET http://localhost:${TARGET_PORT}/api/person/${TEST_ID}" | vegeta attack | vegeta report > /tmp/test.json 
```