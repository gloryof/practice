# flagd練習
## うまくいかなかったこと
下記のエラーが出てきしまいフラグ情報を取得できなかった。
```
dev.openfeature.sdk.exceptions.GeneralError: UNIMPLEMENTED: unknown service flagd.evaluation.v1.Service
	at dev.openfeature.contrib.providers.flagd.resolver.grpc.GrpcResolver.mapError(GrpcResolver.java:385) ~[flagd-0.8.0.jar:na]
	at dev.openfeature.contrib.providers.flagd.resolver.grpc.GrpcResolver.resolve(GrpcResolver.java:176) ~[flagd-0.8.0.jar:na]
	at dev.openfeature.contrib.providers.flagd.resolver.grpc.GrpcResolver.booleanEvaluation(GrpcResolver.java:101) ~[flagd-0.8.0.jar:na]
	at dev.openfeature.contrib.providers.flagd.FlagdProvider.getBooleanEvaluation(FlagdProvider.java:114) ~[flagd-0.8.0.jar:na]
	at jp.glory.practice.flagd.controller.FlagController.get(FlagController.kt:18) ~[main/:na]
```
grpcurlを実行してみても下記のエラーが発生しているので環境設定などに不備がありそう。
```
grpcurl -proto evaluation.proto -plaintext localhost:30085 flagd.evaluation.v1.Service.ResolveBoolean
ERROR:
  Code: Unimplemented
  Message: unknown service flagd.evaluation.v1.Service
```