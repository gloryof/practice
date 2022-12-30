# Spring Boot3の練習場

## TODO
- Production Readyを調べる
  - Observabilityを調べる
  - OpenMetrics/OpenTracingを調べる
  - Auditingを調べる
  - Process Monitoringを調べる
  - Recording HTTP exchangeを調べる
  - https://github.com/spring-projects/spring-boot/issues/33372 の件を調べる
  - http://localhost:8080/actuator/ にアクセスした時にWhitelabelページが出るのを直す
- Problem Detailのtypeの使い方を直す
  - エラーを解決するためのURLを返す
- 入力チェックによるバリデーションを整理する
- ActuatorのSecurityを考える
- k8s環境でGrafanaやZipkinなどを整える
  - GrafanaとGrafana LokiでMetricsとTracingがおえるようにする
  - ZipkinでTracingをおえるようにする

## API
### Register API
#### Create
```
curl -v \
    -X POST \
    -H 'Content-Type:application/json' \
    -d @request/user.json \
    http://localhost:8080/api/register
```
### Authenticate API
#### Generate token
```
curl -v \
    -X POST \
    -H 'Content-Type:application/json' \
    -d @request/auth.json \
    http://localhost:8080/api/auth
```
### User API
#### Get All
```
curl -v \
    -H "Authorization:Bearer ${TOKEN}"  \
    http://localhost:8080/api/users
```
#### Get User
```
curl -v \
    -H "Authorization:Bearer ${TOKEN}"  \
    http://localhost:8080/api/users/test-user-id
```