# SonarQube

## 事前準備
Docker版のSonarqubeを起動する。
```
$ docker pull sonarqube:10.3.0-community
$ docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

## 起動後
1. http://localhost:9000 にアクセスする
2. ID/パスワードはadminでログインする
3. 新しいパスワードの設定を求められるので入力する
4. 「My Account」をクリックし、アカウントページに遷移する
5. 「Security」をクリックし、セキュリティページに遷移する
6. 「Tokens」からトークンを生成する
