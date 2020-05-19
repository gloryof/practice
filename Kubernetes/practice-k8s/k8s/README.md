# k8sの実行

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