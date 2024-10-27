# アプリケーションの設定
### Argoアプリケーションの追加
- General
  - Application Name: canary-app
  - Project Name: default
  - Sync Policy: Automatic
- Source
  - Repository URL: https://github.com/gloryof/practice.git
  - Revision: HEAD
  - Path: ArgoCD/practice-argo-rollout/canary/manifests/nginx
- Destination
  - Cluster URL: https://kubernetes.default.svc
  - Namespace: practice


## 確認
### Rolloutsの確認
```
./script/watch
```

### Rolloutの承認
```
./script/promote
```