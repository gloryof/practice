# JFRの練習場

## 準備
下記のコマンドでダンプ用ディレクトリを作成する
```
mkdir /tmp/dump
```

## 起動方法
起動Optionに下記のコマンドをつける。
```
-XX:StartFlightRecording=dumponexit=true,filename=/tmp/dump/app.jfr,settings=profile.jfc
```

## APIの実行
### Product API
#### 全件取得
```
curl http://localhost:8080/products
```
#### ID取得
```
curl http://localhost:8080/products/product-id-0
```
#### 登録
```
curl  -X POST  -H "Content-Type: application/json" -d @request/register.json http://localhost:8080/products 
```
#### 更新
```
curl  -X PUT  -H "Content-Type: application/json" -d @request/update.json http://localhost:8080/products/product-id-0
```
### Service API
#### 全件取得
```
curl http://localhost:8080/services
```
#### ID取得
```
curl http://localhost:8080/services/service-id-0
```
### Member API
#### 全件取得
```
curl http://localhost:8080/members
```
#### ID取得
```
curl http://localhost:8080/members/member-id-0
```
