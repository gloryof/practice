# DroneによるCI/CD
## 事前準備

### Drone CLI
CLIをインストールする。
https://docs.drone.io/cli/install/

### k8sの実行
下記のコマンドでk8sにあるワークロードを起動する。
```
$ kubectl apply -f k8s --recursive
```