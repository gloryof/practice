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

## 実行方法
### API
```
curl http://localhost:30080/products
```

### ダッシュボード
http://localhost:30494/dashboard