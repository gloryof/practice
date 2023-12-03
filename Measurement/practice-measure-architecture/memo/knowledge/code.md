# コードに関する計測

## 前提知識の整理

### 凝集度
そのコードやモジュールがどれだけ関連した物で集められているかを表す。  
以下の尺度で表され、数字が高いものほど凝集度が高い。

1. 偶発的凝集：関連性がない状態
2. 論理的凝集：データは関連しているが処理は関連していない
3. 時間的凝集：使用順序など一見無関係に見えるが時間的な関連がある
4. 手続き的凝集：特定の順序で呼び出す必要がある
5. 通信的凝集：何かしらの出力（DBなど）により関連している
6. 逐次的凝集：In/Outにより相互に作用する
7. 機能的凝集：関連する要素だけモジュールが構成されている

LCOMメトリクスという計測の仕方が存在する。

### 結合度
そのコードやモジュールがどれだけ結合しているかを表す。  
疎結合である方が再利用しやすい良いとされている。  

- 求心性結合：入力される接続数
- 遠心性結合：出力される接続数

接続数というのは使用しているオブジェクトの数のことを指すっぽい。

### 抽象度
抽象クラスと具象クラスの比率。  
抽象度が高いほど安定しやすい。  

### 不安定度
どれだけ壊れやすいかの尺度。  
`遠心性結合 / (遠心性結合 + 求心性結合)` で計算される。

求心性結合が少ない方が安定する。

### 主系列からの距離
抽象度と不安定度から算出される。  
適度な抽象度と適度な不安定度というのがおそらく「主系列」で、そこからどれだけ離れているかの度合い。  

不安定度がほとんどないのに抽象度が高いと無駄みたいなことになる。

### コナーセンス
コナーセント（接続）という意味からきていて、コード間の接続に関する性質。  
静的なコナーセンスと動的なコナーセンスがある。  

動的なコナーセンスより静的なコナーセンスにすることが望ましい。  
また、後述するコナーセンスの順序として数値が小さいものにするのが望ましい。

#### 静的なコナーセンス
静的なコナーセンスとしては以下がある。

1. 名前のコナーセンス(Connascence Of Name)：名前に合意する
2. 型のコナーセンス(Connascence Of Type)：型に合意する（例：型の制約をつける）
3. 意味のコナーセンス(Connascence Of Meaning)：特定の意味に合意する（例：true=1など）
4. 位置のコナーセンス(Connascence Of Position)：値の順序に合意する（例：パラメータの呼び出し順序）
5. アルゴリズムのコナーセンス(Connascence Of Algorithm)：特定のアルゴリズに合意する（例：ハッシュアルゴリズム）

#### 動的なコナーセンス
動的なコナーセンスとしては以下がある。

1. 実行順序のコナーセンス(Connascence Of Execution)：実行順序に合意する
2. タイミングのコナーセンス(Connascence Of Timing)：実行タイミングに合意する（例：スレッドのレース状態）
3. 値のコナーセンス(Connascence Of Value)：使用する値に合意する。同じ値を複数のコンポーネントで参照する
4. アイデンティティのコナーセンス(Connascence Of Identity)：あるコンポーネントが特定のインスタンスに依存する場合に発生する

### サイクロマティック複雑度
プログラムコードないの分岐がどれだけあるかを示したもの。  
20以上になるとリスクが増えると言われている。