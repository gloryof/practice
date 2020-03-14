# Spring Cloud Configの練習
## 目的
コンテナ環境での効率の良い設定ファイルの運用方法を覚える。  

### 経緯
アプリケーションコンテナを複数立ち上げたときに  
設定ファイルをビルド時に入れた場合でも対応できるが  
Spring Cloud Configで管理した方が効率が良さそうなので試してみる。

## アプリ
アプリのエンドポイントは下記。  
- `/api/config`
- `/api/db`

## やり残していること
### config-server-with-vault
- ローカル起動の整備
- ビルド時のskipTestsをなくす
- ポリシーの整理

#### ローカル起動の整備
必要なインフラを`docker-compose up`のみで起動できる環境を用意する。  
その際、可能であればdev/stageとおなじdocker-compose.ymlが使えると良い。  

`role-id`,`secret-id`を固定にして、vaultを起動する方法を探す。  

#### ビルド時のskipTestsをなくす
mavenビルド時にskipTestsを指定しているのでなくす。  
テスト時のプロファイルを指定するのが良さそう。

#### ポリシーの整理
ポリシーは動かすためだけに追記しているので整理する。