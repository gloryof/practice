# replicaset
## 概要
MongoDBのレプリカセットを覚えるために構築。

## 実行
ワーキングディレクトリは`docker`ディレクトリ。
### ビルド
下記のコマンドを実行する。
```
$ ./build.sh 
```
### 認証オフで起動
レプリカセットの設定をするために認証オフで起動。
```
$ ./change-noauth-mode.sh
```
### レプリカセットを初期化
```
$ mongo localhost/replica-db < ./script/initialize-replicaset.js 
```
実行すると`Primary : ${primary}`と表示される。  
以降の手順では対応するサーバに`mongo`コマンドで接続する。
### ユーザを作成する
```
mongo ${primary}/replica-db < ./script/create-user.js
```
### データをを投入する
```
mongo ${primary}/replica-db < ./script/register-data.js
```
## ダンプ/リストア
### ダンプ
```
mongodump --host localhost --port ${primaryPort} -u test-user -p test-password --db replica-db  -o "/tmp/replica-set-dump"
```
### リストア
```
mongorestore --host localhost --port ${primaryPort} -u test-user -p test-password /tmp/replica-set-dump/replica-db/language.bson
```