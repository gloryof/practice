# コンテナ練習
## 目的
コンテナを利用したビルド、アーティファクトの管理、実行まで学ぶこと。  

## 実行方法
### Docker Registry
docker-registryディレクトリに移動する。

```
cd docker-registry
```

docker-registryを起動する。  

```
docker-compose up -d
```

### Docker
#### ビルド
docker-buildディレクトリに移動する。  

```
cd docker-build
```

ビルドを実行する。

```
docker build .
```

ビルドされたイメージをタグ化.  
イメージIDは最終的にexec-stageのコンテナを対象とする。

```
docker tag {イメージID} localhost:5000/glory_of/java-app:1.0.0
```


ビルドされたexec-stageのコンテナを実行する。

```
docker run -p 8080:8080 glory_of/java-app:1.0.0
```

http://localhost:8080/api/home にアクセスする。

#### registryへの配置 
docker-buildディレクトリに移動する。  

```
cd docker-build
```

タグ付けしたイメージをプッシュする。

```
docker push localhost:5000/glory_of/java-app:1.0.0
```

http://localhost:5000/v2/glory_of/java-app/tags/list にアクセスしてプッシュされていることを確認する。