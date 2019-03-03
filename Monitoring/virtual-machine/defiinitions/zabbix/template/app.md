# アプリケーションサーバ

## 概要
アプリケーションサーバのテンプレート。

## Name
App

## Applications
- Metrics
- GC
- Thread
- DB

### Metrics
- AppStatus

### GC
- JVM-GC-Heap-Survivor
- JVM-GC-Heap-Eden
- JVM-GC-Heap-Old
- JVM-GC-Time-Evacuation
- JVM-GC-Count-Evacuation

### Thread
- Tomcat-Thread-Current
- Tomcat-Thread-Busy
- JVM-Thread-Runnable
- JVM-Thread-Waiting
- JVM-Thread-Timed-Waiting
- JVM-Thread-Blocked

### DB
- DB-Connection-Idle
- DB-Connection-Pending
- DB-Connection-Active

## Items
アイテムの設定。

### AppStatus
`/actuator/prometheus`にアクセスする。  

`Type`は`HTTP agent`。  
設定キーは`app-status`。  
`URL`は`http://192.168.1.110:8080/actuator/prometheus`。  

### JVM-GC-[GcItemGroup]-[GcItem]
G1GCに関するメトリクス。

`Type`は`Dependent Items`。  
`GcItemGroup`は取得するアイテムのグループ、Heap/Count/Timeのいずれか。  
`GcItem`は取得するアイテムの名前。  
設定キーは項目名のLowerCase。  
取得項目ごとの正規表現は下記。  
- `jvm_memory_used_bytes{area="heap",id="G1 Survivor Space",} ([0-9a-zA-Z.]+)`
- `jvm_memory_used_bytes{area="heap",id="G1 Eden Space",} ([0-9a-zA-Z.]+)`
- `jvm_memory_used_bytes{area="heap",id="G1 Old Gen",} ([0-9a-zA-Z.]+)`
- `jvm_gc_pause_seconds_count{action="end of minor GC",cause="G1 Evacuation Pause",} ([0-9]+)`
- `jvm_gc_pause_seconds_sum{action="end of minor GC",cause="G1 Evacuation Pause",} ([0-9.]+)`

### Tomcat-Thread-[ThreadType]
Tomcatのスレッド。  

`Type`は`Dependent Items`。  
`ThreadType`は取得するアイテムの名前。  
設定キーは項目名のLowerCase。  
取得項目ごとの正規表現は下記。  
- `tomcat_threads_current_threads{name="http-nio-8080",} ([0-9]+)`
- `tomcat_threads_busy_threads{name="http-nio-8080",} ([0-9]+)`

### JVM-Thread-[ThreadType]
JVMのスレッド。  

`Type`は`Dependent Items`。  
`ThreadType`は取得するアイテムの名前。  
設定キーは項目名のLowerCase。  
取得項目ごとの正規表現は下記。  
- `jvm_threads_states_threads{state="runnable",} ([0-9]+)`
- `jvm_threads_states_threads{state="waiting",} ([0-9]+)`
- `jvm_threads_states_threads{state="timed-waiting",} ([0-9]+)`
- `jvm_threads_states_threads{state="blocked",} ([0-9]+)`

### DB-Connection-[ConnectionStatus]
DBのコネクション数。

`Type`は`Dependent Items`。  
`ConnectionStatus`は取得するアイテムの名前。  
設定キーは項目名のLowerCase。  
取得項目ごとの正規表現は下記。  

- `hikaricp_connections_idle{pool="HikariPool-1",} ([0-9]+)`
- `hikaricp_connections_pending{pool="HikariPool-1",} ([0-9]+)`
- `hikaricp_connections_active{pool="HikariPool-1",} ([0-9]+)`

## Graphs
グラフの設定。

### GC-Heap
GCのヒープ領域に関するグラフ。  
対象は下記。

- JVM-GC-Heap-Survivor
- JVM-GC-Heap-Eden
- JVM-GC-Heap-Old

### GC-Stats
GCの回数と時間に関するグラフ。  
対象は下記。  

- JVM-GC-Time-Evacuation
- JVM-GC-Count-Evacuation

### Tomcat-Thread
Tomcatのスレッドに関するグラフ。  
対象は下記。  

- Tomcat-Thread-Current
- Tomcat-Thread-Busy

### JVM-Thread
JVMのスレッドに関するグラフ。  
対象は下記。  

- JVM-Thread-Runnable
- JVM-Thread-Waiting
- JVM-Thread-Timed-Waiting
- JVM-Thread-Blocked

### DB-Connection
DBのコネクションに関するグラフ。  
対象は下記。  

- DB-Connection-Idle
- DB-Connection-Pending
- DB-Connection-Active
