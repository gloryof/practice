# Dockerfile置き場
## 構成
- `regitry` : DockerイメージをおくためのDocker Registry
- `web` : Webサーバコンテナ

## ビルド方法
### Docker Registryの起動
```
$ cd registry
$ docker-compose up -d
$ cd ..
```

### ビルドとプッシュ
下記のコマンドでビルドする。  
`targetContainer`は対象のコンテナ名を設定する。  

```
$ docker build -t localhost:5000/glory_of/k8s-${targetContainer}:latest ${targetContainer}
```
