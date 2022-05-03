# !/bin/sh
source ./local-file/local.env

cp -a ./config/rest-assured /tmp
# cp -a ./config/zap /tmp

drone exec --env-file ./local-file/local.env --trusted
