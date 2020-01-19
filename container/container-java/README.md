# コンテナ練習
## 目的
コンテナを利用したビルド、アーティファクトの管理、実行まで学ぶこと。  

## 実行方法

### Dockerビルド
docker-buildディレクトリに移動する。  

```
cd docker-build
```

ビルドを実行する。

```
docker build .
```

ビルドされたexec-stageのコンテナを実行する。

```
docker run -p 8080:8080 ${イメージID}
```

http://localhost:8080/api/homeにアクセスする。
