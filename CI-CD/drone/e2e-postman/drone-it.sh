# !/bin/sh
cp -a ./postman /tmp
# cp -a ./config/zap /tmp

drone exec --env-file ./local-file/local.env --trusted
