# Argo Rolloutの練習場

## 事前準備
### ArgoCDのインストール
https://argo-cd.readthedocs.io/en/stable/getting_started/ を参照。


### Argo Rolloutのインストール
https://argoproj.github.io/argo-rollouts/installation/ を参照。

### namespaceの作成動
```
kubectl create namespace practice
```

### 起動
```
kubectl port-forward svc/argocd-server -n argocd 8080:443
```

https://localhost:8080/ にアクセス。

### Argoアプリケーションの追加
- General
  - Application Name: argo-rollouts-practice-nginx
  - Project Name: default
  - Sync Policy: Automatic
- Source
  - Repository URL: https://github.com/gloryof/practice.git
  - Revision: HEAD
  - Path: ArgoCD/practice-argo-rollout/manifests/nginx
- Destination
  - Cluster URL: https://kubernetes.default.svc
  - Namespace: practice


## 確認
### Rolloutsの確認
```
kubectl argo rollouts get rollout nginx-deployment -n practice
```