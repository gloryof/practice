# Kubernetes
## 目的
Kubernetesの使い方に慣れる。    
Kubernetesを使った構成を考える。

## 事前準備

### ビルド方法
`docker` ディレクトリにあるコンテナをビルドする。  
ビルド方法は対象ディレクトリ配下のREADME.mdを参照。

### Nodeの設定
下記のコマンドを実行しStackdriverを有効にする。

```
$ kubectl label node  docker-desktop beta.kubernetes.io/fluentd-ds-ready=true
```

## 実行と停止
### 実行方法
下記のコマンドを実行し、クラスタを起動する。
```
$ kubectl apply -f k8s -R
```

### 停止方法
```
$ kubectl delete -f k8s -R
```
