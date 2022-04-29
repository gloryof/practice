# !/bin/sh
source ./local-file/local.env

cp -a ./config/postman /tmp
cp -a ./config/rest-assured /tmp
# cp -a ./config/zap /tmp

drone exec --pipeline integration-test --env-file ./local-file/local.env --trusted