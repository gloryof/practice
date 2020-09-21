# Dockerfile置き場
## ビルド方法
### ビルドとプッシュ
下記のコマンドでビルドする。  
`targetContainer`は対象のコンテナ名を設定する。  

```
$ DOCKER_BUILDKIT=1  docker build -t localhost:5000/glory_of/oauth2-${targetContainer}:latest ${targetContainer}
```
