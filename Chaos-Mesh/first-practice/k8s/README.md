# Chaos Meshの練習用k8s

## 事前準備
### Chaos meshのインストール
```
./install-helm.sh 
```

## 起動と停止
### 起動
```
./start.sh
```
### 停止
```
./strop.sh
```

### Chaos ExperimentのON/OFF
#### ON
```
kubectl annotate --overwrite networkchaos ${name} -n chaos-manager experiment.chaos-mesh.org/pause=false
```

#### OFF
```
kubectl annotate --overwrite networkchaos ${name} -n chaos-manager experiment.chaos-mesh.org/pause=true
```


## 実行方法
### API
```
curl http://localhost:30080/products
```

### ダッシュボード
毎回、install/uninstallしているためポートは毎回書き変わる。  
`get svc`を使って毎回調べること。
