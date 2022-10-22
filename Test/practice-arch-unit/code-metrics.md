# コードメトリクス
## Lako Metrics
### Cumulative Component Dependency (CCD)
全てのコンポーネントの依存度の合計。  
この数値が高い場合、依存度が高いということになるため修正による影響が大きくなる。

#### 計算方法
依存の計算は何も依存しないコンポーネントまでが対象となる。  
例えば、A→B、B→C、B→Dのような依存関係の場合、Aの依存度は4となる。  

### Average Component Dependency (ACD)
コンポーネントごとの依存度の平均。  
この数値が高い場合はCCDと同じく依存が高いため、修正による影響が大きくなる。

20を超えてはいけないのような情報がある。  
ただし、デファクトスタンダードのような数字ではなさそう。

#### 計算方法
CCD / コンポーネント数 = ACD

### Relative Average Component Dependency (RACD)
この数値の意味があまりわからない・・・  
ACDをコンポーネント数で割った数らしい。

#### 計算方法
ACD / コンポーネント数 = RACD

### Normalized Cumulative Component Dependency (NCCD)
平衡二分探索木にし正規化されたCCD。  

## Component Dependency Metrics
### Efferent Coupling (Ce)
コンポーネントの依存数。  
数字が大きいほど不安定となる。

### Afferent Coupling (Ca)
コンポーネントの被依存数。

### Instability (I)
不安定性。  
0から1の範囲の値になり、数字が大きほど不安定。

#### 計算方法
Ce / (Ca + Ce) = I

### Abstractness (A)
抽象度。  
この数字が大きいほど抽象化されている。

#### 計算方法
抽象化クラス / 全てのクラス = I

### Distance from Main Sequence (D)
主系列の距離。  
不安定性と抽象度から計算される。  

数値が大きいほど「無駄ゾーン」となり、抽象度が高すぎて使いにくい。  
数値が小さいほど「苦痛ゾーン」となり、具体度が高すぎてメンテナンスがしにくい。

#### 計算方法
| A + I - 1 | = D

## Visibility Metrics
### Relative Visibility (RV)
コンポーネントごとの相対的な可視性。

#### 計算方法
コンポーネントごとのvisible_elements / コンポーネント内の全elements = RV 

### Average Relative Visibility (ARV)
RVの平均。

#### 計算方法
コンポーネントのRV合計 / コンポーネント = ARV

### Global Relative Visibility (GRV)
相対的な可視性。  
RVとは違いソフトウェア内の全てのelementsを対象として計算する。

#### 計算方法
visible_elements / 全elements = GRV 
