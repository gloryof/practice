# Kubernetes
## 目的
Kubernetesの使い方に慣れる。    
Kubernetesを使った構成を考える。

## 実行環境
`minikube` を使って構成している。

## 実行方法
下記のコマンドを実行し、クラスタを起動する。
```
$ kubectl apply -f k8s
```

下記のコマンドを実行し、動作していることを確認する。
```
$ minikube service web-service 
```
