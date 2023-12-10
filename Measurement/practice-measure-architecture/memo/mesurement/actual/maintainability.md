# 保守性

## 事前準備
[SonarQube](./sonar-qube.md)

## 静的コード分析
### コードの行数
SonarQubeによる計測をした。    
「Measures」->「Size」にて確認ができる。

### サイクロマティック複雑度
SonarQubeによる計測をした。  
「Measures」->「Complexity」にて確認ができる。

### 警告数
SonarQubeによるIssue管理により数の計測はできる。  
すべての警告を潰せるわけではないが、Linterと組み合わせればある程度は減らせそう。

## ビルド時間
Gradleの起動オプションである `--profile` を使用する。  
`--profile` を利用することより`build/reports`ディレクトリにレポートのHTMLファイルが出力される。  

**GitHub actionsの検証が必要**