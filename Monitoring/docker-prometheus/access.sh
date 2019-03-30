    
# /bin/bash
# Command + c で終了
# 環境変数 API_HOST にホストを設定する（デフォルトはlocalhost）
# 環境変数 API_PORT にポートを設定する（デフォルトは8080）
# 

apiHost="127.0.0.1"
apiPort="80"

if [[ ! -z ${API_HOST} ]]; then
  apiHost=${API_HOST}
fi

if [[ ! -z ${API_PORT} ]]; then
  apiHost=${API_PORT}
fi

function callApi() {
  url="http://${apiHost}:${apiPort}/users/all"
  
  curl ${url} > /dev/null
}


while true
do

callApi "Error"

done