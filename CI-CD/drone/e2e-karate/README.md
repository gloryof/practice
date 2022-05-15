# Karate によるE2Eテスト
## 事前準備
### ローカル用環境変数ファイルを作成する
Gitリポジトリにあげない環境変数を作成する。

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
## 動作方法
### Build
下記のコマンドを実行する。
```
$ ../drone-by-gradle.sh 
```
