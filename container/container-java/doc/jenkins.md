# Jenkins
元々はDocker Composeを使ってDocker上でJenkinsコンテナを実行しようとしたが  
そのJenkinsコンテナ上からDockerビルドがうまくいかないようなのでローカルで実行する。

## インストール
下記のURLからWARパッケージでダウンロードする。  
https://jenkins.io/download/

## 実行環境
- Java 11

## 実行方法
```
java -jar jenkins.war --httpPort=8081
```

## 設定
### Global Tool
- Maven 3.6.3をインストール（名前：maven3.6.3）
- Dockerのlatestをインストール（名前：docker-latest）

### 認証情報
- docker-java: デプロイ先ホストのdocker-javaの鍵認証情報