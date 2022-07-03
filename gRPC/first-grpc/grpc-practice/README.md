# gRPCの練習場

## 準備
### grpccurlのインストール
下記のコマンドでgrpccurlをインストールする。
```
$ brew install grpcurl
```
パスに `/usr/local/Cellar/grpcurl/${version}/bin/`を設定する。  
インストールの仕方によって入れる必要がないかも？

## 実行方法
標準入力から実行する場合はControl+dを押す。
### All service
#### list
```
$ grpcurl -plaintext localhost:6565 list
```
### Product
#### list
```
$ grpcurl -plaintext localhost:6565 list product.ProductService
```

#### GetProducts
```
$ grpcurl -plaintext localhost:6565 product.ProductService/GetProducts 
```

#### GetProduct
```
$ grpcurl -plaintext -d @ localhost:6565 product.ProductService/GetProduct

{
  "id": "hoge"
}
```

## TODO
### gRPC Spring Boot StarterからgRPC純正に移行しようとしたけどうまくいかない。
下記のエラーが出力されサーバが起動しない。
```
Execution failed for task ':generateProto'.
> protoc: stdout: . stderr: protoc-gen-kotlin: program not found or is not executable
  --kotlin_out: protoc-gen-kotlin: Plugin failed with status code 1.
```
### エラーの型を定義する
- google.rpc.ErrorInfoがうまくインポートできない
  - インポート方法を調査するか独自の型で対応する
