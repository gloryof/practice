# Kubernetes
## 目的
Kubernetesの使い方に慣れる。    
Kubernetesを使った構成を考える。

## 環境
- helm
- nginx-ingress

### helm
下記のコマンドでhelmをインストールする。
```
$ brew install helm
```

### nginx-ingress
下記のコマンドでhelmをインストールする

```
$ helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
$ helm install external-load-balancer ingress-nginx/ingress-nginx \
    --set controller.ingressClass=external-load-balancer
```

## 実行方法
下記のコマンドを実行し、クラスタを起動する。
```
$ kubectl apply -f k8s
```
