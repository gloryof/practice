# Buildpacks
## インストール
```
brew tap buildpack/tap
brew install pack
```
## ビルド
container-javaディレクトリ配下で下記のコマンドを実行する。 

```
pack build localhost:5000/glory_of/java-app-packs  --path java-app/  --builder cloudfoundry/cnb:bionic 
```
## registryへの配置 
タグ付けしたイメージをプッシュする。

```
docker push localhost:5000/glory_of/java-app-packs:latest
```