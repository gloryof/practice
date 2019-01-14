# /bin/bash
# Command + c で終了
# 環境変数 API_HOST にホストを設定する（デフォルトはlocalhost）
# 環境変数 API_PORT にポートを設定する（デフォルトは8080）
# 

apiHost="localhost"
apiPort="8080"

if [[ ! -z ${API_HOST} ]]; then
  apiHost=${API_HOST}
fi

if [[ ! -z ${API_PORT} ]]; then
  apiHost=${API_PORT}
fi

function callApi() {
  url="http://${apiHost}:${apiPort}/search"
  file="@./search/pattern$1.json"

  curl  -H "Content-Type: application/json"  ${url} -v -d $file > /dev/null
}

while true
do
  randV=$((($RANDOM % 5)+1)) 
  callApi ${randV}
done