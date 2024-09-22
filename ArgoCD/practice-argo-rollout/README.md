# Argo Rolloutの練習場

## ArgoCDのインストール
https://argo-cd.readthedocs.io/en/stable/getting_started/ を参照。


## 起動
```
kubectl port-forward svc/argocd-server -n argocd 8080:443
```

https://localhost:8080/ にアクセス。