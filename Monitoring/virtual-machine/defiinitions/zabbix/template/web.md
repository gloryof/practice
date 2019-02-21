# Web

## 概要
Web設定のテンプレート。

## Name
Web

## Applications
- Worker
- Access

### Worker
- Worker-BusyWorkers
- Worker-IdleWorkers

### Access
- Access-Count
- Access-Time

## Items
アイテムの設定。

### Worker-[WorkerType]
ワーカの数に関する設定。  
`[WorkerType]`にはBusyWorkers/IdleWorkersのうちのいずれかが設定される。  

`Type`は`Zabbix agent`。  
`Type of information`は`Numeric(unsigned)`。 

設定キーは下記。  
- `apache.server_status["BusyWorkers"]`
- `apache.server_status["IdleWorkers"]`

### Access-Count
アクセス数に関する設定。  
`Type`は`Zabbix agent`。  
`Type of information`は`Numeric(unsigned)`。 
`Preprocessing`で`SimpleChange`を追加する。  

設定キーは`apache.server_status["Total Accesses"]`。

## Web scenarios
### Access-Time
アクセスのレスポンス時間に関する設定。  

下記のURLにアクセスするステップを設定する。  
- http://192.168.1.112/users

## Graphs
グラフの設定。

### Workers
ワーカのグラフ。  
対象は下記。

- Worker-BusyWorkers
- Worker-IdleWorkers

### Access-Count
アクセス数のグラフ。  
対象は下記。  

- Access-Count

### Access-Time
アクセス時間のグラフ。  
対象は下記。  
- Response time for step "users" of scenario "Access-Time".
