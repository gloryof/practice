## 仮想環境
### 環境情報
- OS:CentOS 8.1-1911
### Dockerのインストール
下記のページを参考にしてインストール。  
https://qiita.com/dora_2562/items/24691d3bec4c99c1d794
### hostsの変更
ホストOSのIPアドレスが変わったりすると面倒なので名前をつけて対応。   
名前：`local.docker.registry`  
### ユーザの追加
rootユーザでログインし、下記のコマンドを実行する。

```
useradd docker-java
```

鍵認証により接続するので`docker-java`用の鍵を作成すること。

### ローカルDocker Registryの設定
ローカルのDocker Registryに接続するための設定をする。  

rootユーザでログインし、下記のコマンドを実行する。

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
### sudoの設定
rootユーザでログインし、下記のコマンドを実行する。

```
vi /etc/sudoers.d/java-docker 
```

下記の内容を書き込む。
```
docker-java ALL=(ALL) NOPASSWD: /usr/bin/docker
```

### イメージのpull
```
sudo docker pull local.docker.registry:5000/glory_of/java-app:nightly
```

### Dockerコンテナの起動
```
sudo docker run -p 8080:8080 local.docker.registry:5000/glory_of/java-app:nightly
```