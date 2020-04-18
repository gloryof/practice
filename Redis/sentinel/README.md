# Redis Sentinel
## 目的
Redis Sentinelを構築し、Redis Sentinelの仕組みを知る。

## 動かし方

```
$ docker-compose up -d
```

## 動作確認

マスタに接続し、値が設定できることを確認する。
```
$ redis-cli -p 10001

> SET key1 value1 
> GET key1
value1
> exit
```

スレーブに接続し、値が取得できるが設定はできないことを確認する。  
（スレーブPortは10002、10003）
```
$ redis-cli -p ${スレーブPort}

> GET key1
value1
> SET key2 value2
(error) READONLY You can't write against a read only replica.
> exit
```

別ターミナルを立ち上げてSentinelのログを開いた状態にする。
```
$ docker-compose logs -f sentinel1 sentinel2 sentinel3
```

マスターを停止する。  
しばらく待つと待機していたSentinelのターミナルにログが出力されることを確認する。
```
$ docker-compose stop master
```

スレーブがマスタに昇格したことを確認する。
```
$ redis-cli -p 10002 info | grep role
$ redis-cli -p 10003 info | grep role
```
上記のコマンドで `role:master` になったノードにアクセスし、値が設定できることを確認する。
```
$ redis-cli -p ${role:masterのPort}

> SET key2 value2
> GET key2
"value2"
> exit
```

`role:master` になったノードにアクセスし、値が取得できるが設定はできないことを確認する。  
```
$ redis-cli -p ${role:slaveのPort}

> GET key2
"value2"
> SET key3 value3
(error) READONLY You can't write against a read only replica.
> exit
```

旧マスタを立ち上げる。
```
$ docker-compose up -d master
```
スレーブとして立ち上がり、ダウンしている間に設定された値が反映されていることを確認する。
```
$ redis-cli -p 10001 info | grep role
role:slave
$ redis-cli -p 10001
> GET key2
"value2"
> SET key3 value3
(error) READONLY You can't write against a read only replica.
> exit
```