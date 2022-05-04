# CI/CDの対象アプリケーション
## 事前準備
### ローカル用環境変数ファイルを作成する
Gitリポジトリにあげない環境変数を作成する（トークンとか）

下記のディレクトリを作成する。  
```
mkdir ./local-file
```
ファイルを作成する。
```
vi ./local-file/local.env
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
下記のコマンドを実行する。
```
$ ./drone-build.sh
```