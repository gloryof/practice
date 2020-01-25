## 仮想環境
### 環境情報
- OS:CentOS 8.1-1911
### Dockerのインストール
下記のページを参考にしてインストール。  
https://qiita.com/dora_2562/items/24691d3bec4c99c1d794
### hostsの変更
ホストOSのIPアドレスが変わったりすると面倒なので名前をつけて対応。   
名前：`local.docker.registry`  
### ローカルDocker Registryの設定
ローカルのDocker Registryに接続するための設定をする。  

rootユーザでログインし（もしくはsudoをつける）、下記のコマンドを実行する。

```
vi /etc/docker/daemon.json
```

下記の内容を書き込む。

```
{
  "insecure-registries" : ["local.docker.registry:5000"]
}
```

dockerを再起動する。

```
sudo service docker restart
```

### イメージのpull
```
docker pull local.docker.registry:5000/glory_of/java-app:1.0.0
```

### Dockerコンテナの起動
```
docker run -p 8080:8080 local.docker.registry:5000/glory_of/java-app:1.0.0
```