# single-node
## 概要
MongoDBの基本的な動作を覚えるためにMongoDBのサーバ自体はシングルノードで構築する。  
エンジニアのスキルシート的なデータを想定する。  

### トランザクションデータ
エンジニアのプロフィールを表すデータをトランザクションデータとする。  
保持する項目としては下記。
- 名前（name）
- 生年月日（birthDay）
- 自己PR（myself）
- スキル（skill）
  - 言語（リスト）（languages）
    - 言語コード（languageCode）
    - 経験年月（experience）
      - 経験年（year）
      - 経験月（month）
  - データストア（datastores）
    - データストアコード（dataStoreCode）
    - 経験年月（experience）
      - 経験年（year）
      - 経験月（month）

### マスタデータ
マスタデータとして持つのは下記。  
- 言語
- データストア
- データストアタイプ
#### 言語
プログラミング言語を表すデータ。

|languageCode|name|sinceYear|
|-------------|----|----------|
|1|Java|1995|
|2|Ruby|1995|
|3|Scala|2003|
|4|Go|2009|
|5|Kotlin|2011|

#### データストア
データストアを表すデータ。  

|dataStoreCode|name|sinceYar|dataStoreTypeCode|
|---------------|----|----------|--------------------|
|1|Oracle Database|1977|1|
|2|Microsoft SQL Server|1989|1|
|3|MySQL|1995|1|
|4|PostgreSQL|1997|1|
|5|Neo4j|2007|3|
|6|Apache Cassandra|2008|4|
|7|MongoDB|2009|2|

#### データストアタイプ
データストアのタイプを表すデータ。

|dataStoreTypeCode|name|
|--------------------|----|
|1|RDB|
|2|ドキュメントストア|
|3|グラフ|
|4|キーバリューストア|

## 実行
### IDをキーにデータを取得する
```
$ mongo localhost/single-db -u test-user -p test-password < query/findByUserId.js
```

### 複数のIDをキーにデータを取得する
```
$ mongo localhost/single-db -u test-user -p test-password < query/findByUserIds.js
```
