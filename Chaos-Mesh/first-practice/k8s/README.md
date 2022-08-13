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
#### Kindと名前の対応表

|Kind|Name|
|----|----|
|NetworkChaos|network-delay|
|JVMChaos|jvm-exception|

#### ON
```
kubectl annotate --overwrite ${Kind} ${Name} -n chaos-manager experiment.chaos-mesh.org/pause=false
```

#### OFF
```
kubectl annotate --overwrite ${Kind} ${name} -n chaos-manager experiment.chaos-mesh.org/pause=true
```

## 実行方法
### API
```
curl http://localhost:30080/products
```

### ダッシュボード
毎回、install/uninstallしているためポートは毎回書き変わる。  
`get svc`を使って毎回調べること。

## できていないこと
### JVMChaos
JVMChaosの場合、Bytemanをエージェントとして起動する必要がある。  
ただ、Jig経由でBytemanを設定する方法がわからないため調査が必要。