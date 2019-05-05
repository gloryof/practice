    
# /bin/bash
# Command + c で終了
# 環境変数 TARGET_HOST にホストを設定する（デフォルトは127.0.0.1）
# 環境変数 API_PORT にポートを設定する（デフォルトは80）
# 環境変数 FRONT__PORT にポートを設定する（デフォルトは8000）
# 

targetHost="127.0.0.1"
apiPort="80"
frontPort="8000"

if [[ ! -z ${TARGET_HOST} ]]; then
  targetHost=${TARGET_HOST}
fi

if [[ ! -z ${API_PORT} ]]; then
  apiPort=${API_PORT}
fi

if [[ ! -z ${FRONT_PORT} ]]; then
  frontPort=${FRONT_PORT}
fi

function callApi() {
  url="http://${targetHost}:${apiPort}/users/all"
  
  curl ${url} > /dev/null
}

function callFront() {
  url="http://${targetHost}:${frontPort}/api/users/all"
  
  curl ${url} > /dev/null
}


while true
do

callApi
callFront

done