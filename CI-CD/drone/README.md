# DroneによるCI/CD
## 事前準備

### Drone CLI
CLIをインストールする。
https://docs.drone.io/cli/install/

### k8sの実行
下記のコマンドでk8sにあるワークロードを起動する。
```
$ kubectl apply -f k8s --recursive
```

### ローカル用環境変数ファイルを作成する
Gitリポジトリにあげない環境変数を作成する（トークンとか）

下記のディレクトリを作成する。  
```
mkdir ./ci-cd-practice-app/local-file
```
ファイルを作成する。
```
vi ./ci-cd-practice-app/local-file/local.env
```
必要な環境変数は下記。

- JIB_TARGET_HOST:JIBの対象ホスト（localhostだと接続できないため）
- SONARQUBE_TOKEN:Sonarqubeの対象プロジェクトのトークン
- SONARQUBE_HOST_URL:Sonarqubeの対象ホストURL（ホスト名がlocalhostだと接続できないため）


## 動作方法
アプリのディレクトリに移動する。
```
$ cd ./ci-cd-practice-app
```

下記のコマンドを実行する。
```
$ ./drone-build.sh
```