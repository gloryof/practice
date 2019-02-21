# OSベーシック

## 概要
OSの基本的な設定のテンプレート。

## Name
OSBasic

## Applications
- CPU
- IO
- Memory

### CPU
- LA-Avg-1
- LA-Avg-5
- LA-Avg-15
- CPU-User-1
- CPU-User-5
- CPU-User-15
- CPU-System-1
- CPU-System-5
- CPU-System-15
- CPU-Idle-1
- CPU-Idle-5
- CPU-Idle-15

### IO
- Disk-Read-1
- Disk-Read-5
- Disk-Read-15
- Disk-Write-1
- Disk-Write-5
- Disk-Write-15

### Memory
- Memory-Used
- Memory-Available
- Memory-SwapIn
- Memory-SwapOut

## Items
アイテムの設定。

### LA-Avg-[Min]
LA-Avg-1/LA-Avg-5/LA-Avg-15の3種類。  
それぞれロードアベレージの1/5/15分間を表す。  

`Type`は`Zabbix agent`。  
`Type of information`は`Numeric(float)`。  
設定キーは下記。
- `system.cpu.load[all,avg1]`
- `system.cpu.load[all,avg5]`
- `system.cpu.load[all,avg15]`

### CPU-[CpuType]-[Min]
CPUの使用率に関する設定。  
`[CpuType]`にはUser/System/Idleの3つのうちのいずれかが設定される。  
`[Min]`には1/5/15分のいずれかが設定される。

`Type`は`Zabbix agent`。  
`Type of information`は`Numeric(float)`。  
設定キーは下記。
- `system.cpu.util[,user,avg1]`
- `system.cpu.util[,user,avg5]`
- `system.cpu.util[,user,avg15]`
- `system.cpu.util[,system,avg1]`
- `system.cpu.util[,system,avg5]`
- `system.cpu.util[,system,avg15]`
- `system.cpu.util[,idle,avg1]`
- `system.cpu.util[,idle,avg5]`
- `system.cpu.util[,idle,avg15]`

### Disk-[IOType]-[Min]
ディスクのIOに関する設定。  
`[IOType]`にはRead/Writeのいずれかが設定される。  
`[Min]`には1/5/15分のいずれかが設定される。

`Type`は`Zabbix agent`。  
`Type of information`は`Numeric(unsigned)`。  
設定キーは下記。
- `vfs.dev.read[,sps,avg1]`
- `vfs.dev.read[,sps,avg5]`
- `vfs.dev.read[,sps,avg15]`
- `vfs.dev.write[,sps,avg1]`
- `vfs.dev.write[,sps,avg5]`
- `vfs.dev.write[,sps,avg15]`

### Memory-[MemType]
メモリの使用状況に関する設定。  
`MemType`にはUsed/Available/SwapIn/SwapOutのいずれかが設定される。  

`Type`は`Zabbix agent`。  
`Type of information`は`Numeric(unsigned)`。  
設定キーは下記。
- `vm.memory.size[used]`
- `vm.memory.size[available]`
- `system.swap.in[,pages]`
- `system.swap.out[,pages]`

## Graphs
グラフの設定。

### Load Average
ロードアベレージのグラフ。  
対象は下記。

- `OSBasic: LA-Avg-1`
- `OSBasic: LA-Avg-5`
- `OSBasic: LA-Avg-15`

### CPU-Usage-1min
CPU使用率の1分間のグラフ。  
対象は下記。

- `OSBasic: CPU-Idle-1`
- `OSBasic: CPU-System-1`
- `OSBasic: CPU-User-1`

### CPU-Usage-5min
CPU使用率の5分間のグラフ。  
対象は下記。

- `OSBasic: CPU-Idle-5`
- `OSBasic: CPU-System-5`
- `OSBasic: CPU-User-5`

### CPU-Usage-15min
CPU使用率の15分間のグラフ。  
対象は下記。

- `OSBasic: CPU-Idle-15`
- `OSBasic: CPU-System-15`
- `OSBasic: CPU-User-15`

### Disk-IO-1min
ディスクIOの1分間のグラフ。  
対象は下記。  

- `Disk-Read-1`
- `Disk-Write-1`

### Disk-IO-5min
ディスクIOの5分間のグラフ。  
対象は下記。  

- `Disk-Read-5`
- `Disk-Write-5`

### Disk-IO-15min
ディスクIOの15分間のグラフ。  
対象は下記。  

- `Disk-Read-15`
- `Disk-Write-15`

### Memory-Use
メモリの使用量のグラフ。  
対象は下記。

- `Memory-Used`
- `Memory-Available`

### Memory-Swap
メモリスワップのグラフ。  
対象は下記。

- `Memory-SwapIn`
- `Memory-SwapOut`
