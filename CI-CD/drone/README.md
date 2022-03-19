# DroneによるCI/CD
## 事前準備

### Drone CLI
CLIをインストールする。
https://docs.drone.io/cli/install/

### Postman
Postmanをインストールする。

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
#### 環境変数
必要な環境変数は下記。

- GRADLE_UESR_DIR:".gradle/user"固定
- SONARQUBE_KEY_NAME:"ci-cd-practice-app"固定
- JIB_TARGET_HOST:JIBの対象ホスト（localhostだと接続できないため）
- SONARQUBE_TOKEN:Sonarqubeの対象プロジェクトのトークン
- SONARQUBE_HOST_URL:Sonarqubeの対象ホストURL（ホスト名がlocalhostだと接続できないため）


## 動作方法
### Build
アプリのディレクトリに移動する。
```
$ cd ./ci-cd-practice-app
```

下記のコマンドを実行する。
```
$ ./drone-build.sh
```

### Integration test
BuildしたイメージをPullする。
```
$ docker pull localhost:30500/ci-cd-practice-app
```

下記のコマンドを実行する。
```
$ ./drone-it.sh
```

### Postman
#### 環境変数
|変数名|設定値|
|-----|-----|
|user-id|ユーザID|
|password|パスワード|
|baseUrl|ベースとなるURL（http://localhost:8080）|

## うまくいかなかったこと
### OWASP ZAP Docker
Contextファイルを読み込んで認証を通せない。  
問題点としては2つ。  

- Contextファイルの読み込み
- スクリプトのロード

#### Contextファイルの読み込み
Contextファイルのロードに失敗する。  
OWASP ZAP Dockerからではなく、OWASP ZAPからインポートしてもエラーとなる。  

#### スクリプトのロード
認証を行うためにスクリプトファイルをロードする必要がある。  
だが、起動時のパラメータとして渡してもロードされない。  

パラメータの渡し方が間違っているのかそのほかの理由かは不明。