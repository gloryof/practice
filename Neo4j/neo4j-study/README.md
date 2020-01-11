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
### Dockerの準備
dockerディレクトリに移動する。
```
cd ./env/docker
```
dockerを起動する。
```
docker-compose up
```
neo4jのGUIツールを開く。  
http://localhost:7474/browser/  

ログインユーザのパスワードを「password」に変更する。    
docker環境で自動設定する方法が見つけられなかった、かつ、そこに時間をかけたくないので手動。  

### Srping Bootの起動
```
./gradlew bootRun
```

## APIの実行方法
### 組織
#### 組織一覧の取得
```
curl http://localhost:8080/api/organization
```
#### 従業員を入社させる
```
curl -v -X POST -H 'Content-Type:application/json' -d @request/organization/entry.json http://localhost:8080/api/organization/entry
```

#### 従業員を部署に所属させる
```
curl -v -X POST -H 'Content-Type:application/json' -d @request/organization/join.json http://localhost:8080/api/organization/join
```
#### 従業員を部署から離任させる
```
curl -v -X POST -H 'Content-Type:application/json' -d @request/organization/leave.json http://localhost:8080/api/organization/leave
```

#### 従業員を退職させる
```
curl -v -X POST -H 'Content-Type:application/json' -d @request/organization/retire.json http://localhost:8080/api/organization/retire
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
#### 取得
```
curl -v -H 'Content-Type:application/json'  http://localhost:8080/api/employee/{id}/detail  
```
#### 更新
```
curl -v -X PUT -H 'Content-Type:application/json' -d '{"lastName": "テスト姓(変更後)", "firstName": "テスト名(変更後)", "age": 46}' http://localhost:8080/api/employee/{id}  
```

## Next Step
今後の改良課題など。  

- 親部署を削除したときの子部署の処理
- 未所属従業員の表示処理
- 部長、副部長などの役職の追加
- 入力チェックの強化

### 親部署を削除したときの子部署の処理
部署を削除したときにその部署に紐づく子部署が削除されない。  
本来であれば親部署がなくなれば子部署も無くなるので対応する必要がある。

### 未所属従業員の表示処理
現在の処理だと何かしらの部署に所属していないと従業員が表示されない。  
基本的には部署に所属しているのが正しいが  
入社前の人を登録したいなどの要望があると対応できないので修正する。  

仕様的なまとめをどうするかという話なので、Neo4jの使い方に関するところは少なさそう。  
無視してよさそう。

### 部長、副部長などの役職の追加
部署には役割・役職が設けられているので対応する必要がある。

### 入力チェックの強化
入力チェックを実装していないので実装する。  
Neo4jが関係するところがなさそうなので無視しても良いかも。