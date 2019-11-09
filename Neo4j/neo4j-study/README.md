# README

## アーキテクチャ
### 言語
Kotlinを使用する。  
勉強用コードをKotlinで書いたほうがKotlinを覚えられるのでKotlinを使用する。  

### パターン
オニオンアーキテクチャをベースにする。  
ドメインレイヤ、ユースケースレイヤ、インフラレイヤ、Webレイヤで構成する。  

Neo4jを使う際、このレイヤ構成ではどのようなコードになるかを試すために業務コードに近いコードにしている。  

入力チェックは今回の興味の対象がないなので省略する。  
本来は書くべきだが時間かかるので省略。  


### フレームワーク
Spring Bootを使用する。  
実際の業務で使うとなった場合は現時点ではSpring Bootが一番可能性が高いため。  
  
#### Spring Data Neo4j
Neo4jへのアクセスライブラリとしてはSpring Data Neo4j(SDN)を使用する。  
トランザクション制御や低レイヤの処理は業務コードでも触ることは少ないので  
ライブラリに全てお任せしたいのでSDNを使う。  

本来であればSDNが提供するRepositoryが自動生成するクエリを利用すれば良いが、  
今回は業務コードで想定される仕様に対してどのようなクエリが発行されるかを学ぶため  
`@Query`アノテーションで全てハードコーディングする。      

## 起動方法
```
./gradlew bootRun
```

## APIの実行方法
### 組織
#### 組織一覧の取得
```
curl http://localhost:8080/api/organization
```
#### 従業員を部署に所属させる
```
curl -v -X POST -H 'Content-Type:application/json' -d '{"postId": ${postId}, "employeeId": ${employeeId}}' http://localhost:8080/api/organization/join
```
#### 従業員を部署から外す
```
curl -v -X POST -H 'Content-Type:application/json' -d '{"postId": ${postId}, "employeeId": ${employeeId}}' http://localhost:8080/api/organization/leave
```

### 部署
#### 追加
```
curl -v -X POST -H 'Content-Type:application/json' -d '{"name": "部署名"}' http://localhost:8080/api/post  
```
#### 変更
```
curl -v -X PUT -H 'Content-Type:application/json' -d '{"name": "変更後部署名"}' http://localhost:8080/api/post/{id}  
```
#### 削除
```
curl -v -X DELETE -H 'Content-Type:application/json' http://localhost:8080/api/post/{id}  
```
#### 子部署の追加
```
curl -v -X POST -H 'Content-Type:application/json' http://localhost:8080/api/post/{id}/{childId}
```

### 従業員
#### 追加
```
curl -v -X POST -H 'Content-Type:application/json' -d '{"lastName": "テスト姓", "firstName": "テスト名", "age": 45}' http://localhost:8080/api/employee  
```
#### 変更
```
curl -v -X PUT -H 'Content-Type:application/json' -d '{"lastName": "テスト姓(変更後)", "firstName": "テスト名(変更後)", "age": 46}' http://localhost:8080/api/employee/{id}  
```
#### 削除
```
curl -v -X DELETE -H 'Content-Type:application/json' http://localhost:8080/api/employee/{id}  
```