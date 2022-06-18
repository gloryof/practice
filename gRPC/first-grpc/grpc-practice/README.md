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
