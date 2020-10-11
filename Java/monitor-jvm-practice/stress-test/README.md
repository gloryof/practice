# 負荷テスト
## ツール
- [Vegeta](https://github.com/tsenart/vegeta)

## 負荷の掛け方
```
$ export targetPort=30080
$ echo "GET http://localhost:${targetPort}/api/goods" | vegeta attack | vegeta report > /tmp/test.json 
```