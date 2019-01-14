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
  url="http://${apiHost}:${apiPort}/log?result=$1"
  
  if [[ ! -z $2 ]]; then
    url="${url}&status=$2"
  fi

  curl ${url} > /dev/null
}


while true
do
  randV=$(($RANDOM % 10))
  
  if [ ${randV} = 0 ]; then
    callApi "Great" 200
  elif [ ${randV} = 1 ]; then
    callApi "Soso" 200
  elif [ ${randV} = 2 ]; then
    callApi "Bad" 200
  elif [ ${randV} = 3 ]; then
    callApi "Great" 400
  elif [ ${randV} = 4 ]; then
    callApi "Soso" 400
  elif [ ${randV} = 5 ]; then
    callApi "Bad" 400
  elif [ ${randV} = 6 ]; then
    callApi "Great" 404
  elif [ ${randV} = 7 ]; then
    callApi "Soso" 404
  elif [ ${randV} = 8 ]; then
    callApi "Bad" 404
  elif [ ${randV} = 9 ]; then
    callApi "Error"
  fi

done