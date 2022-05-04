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