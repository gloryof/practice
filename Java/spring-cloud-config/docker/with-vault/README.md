# with-vault
HashiCorp Vaultを組み合わせたもの。

## 前提条件
- ローカルにHashiCorp Vaultがインストールされていること

## 起動/ビルド方法
初期ディレクトリはこの`README.md`が配置されているディレクトリとする。  

アプリとConfigサーバを除いたコンテナをビルドする。  

```
$ docker-compose build dev-db stage-db vault mq
```

DBのコンテナを起動する。  

```
$ docker-compose up -d dev-db stage-db
```

VaultとRabbitMQを起動する

```
$ docker-compose up -d vault mq
```

下記のコマンドを実行しVaultの初期化を行う。  
（pushd/popdがない場合はcdで代用する）

```
$ pushd script/vault/
$ ./init.sh 
$ ./put-key.sh
$ popd
```

アプリとConfigサーバのコンテナをビルドする。  
（キャッシュされていると`git checkout`最新版がチェックアウトされないのでキャッシュを無効化している）

```
$ docker-compose build --no-cache dev-app stage-app static-app config-server
```

Configサーバを起動する。

```
$ docker-compose up -d config-server
```

下記のURLにアクセスし、設定情報がロードされたことを確認する。  
（ベーシック認証のユーザは`client`、パスワードは`docker-password`で認証できる）

- http://localhost:8888/config_app/dev
- http://localhost:8888/config_app/stage
- http://localhost:8888/config_app/static

設定情報のロードされていない場合は初期化が済んでいない旨のメッセージが表示されるので、  
ロードはスケジューリングされて実行されるようなので少しまってからリロードすること。  
10秒ほど待てばロードされる。  

アプリを起動する。

```
$ docker-compose up -d dev-app stage-app static-app
```

### Vaultを再起動した場合
Vaultは再起動した際にsealed状態になる。  
いわゆるロック状態のようなもので、再起動後は解除する必要がある。  

下記のコマンドを実行し、unseal（ロック解除）する。  

```
$ pushd script/vault/
$ ./re-unseal.sh 
$ popd
```
### 設定値の動的変更
Vaultの設定値を変える。  

```
$ pushd script/vault/
$ ./change-key.sh 
$ popd
```

下記のURLにアクセスし、`config.vault.value`の値が変わっていることを確認する。  
(staticは動的変更には未対応)

- http://localhost:8888/config_app/dev
- http://localhost:8888/config_app/stage

下記のURLにアクセスし、変更前のアプリ側の値を確認する。  

- http://localhost:8081/api/config
- http://localhost:8082/api/config

設定値の値を反映する。

```
$ curl -X POST http://localhost:8081/actuator/bus-refresh
```