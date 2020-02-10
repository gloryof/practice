# ローカルビルド
## ビルド
docker-build/localディレクトリに移動する。  

```
cd docker-build/local
```

ビルドを実行する。

```
export DOCKER_BUILDKIT=1
docker build --secret id=secret-config,src=${conatainer-javaパス}/deploy/conf/app/secret.yml .
```

ビルドされたイメージをタグ化.  
イメージIDは最終的にexec-stageのコンテナを対象とする。

```
docker tag {イメージID} localhost:5000/glory_of/java-app:latest
```

ビルドされたexec-stageのコンテナを実行する。

```
docker run -p 8080:8080 localhost:5000/glory_of/java-app:latest
```

http://localhost:8080/api/home にアクセスする。

## registryへの配置 
タグ付けしたイメージをプッシュする。

```
docker push localhost:5000/glory_of/java-app:1.0.0
```

http://localhost:5000/v2/glory_of/java-app/tags/list にアクセスしてプッシュされていることを確認する。