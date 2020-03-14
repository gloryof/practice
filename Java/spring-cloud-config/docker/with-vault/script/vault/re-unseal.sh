# /bin/sh

export VAULT_ADDR="http://127.0.0.1:8200"
ENV_FILE=conf/envfile

source ${ENV_FILE}

vault operator unseal ${UNSEAL_KEY1}
vault operator unseal ${UNSEAL_KEY2}
vault operator unseal ${UNSEAL_KEY3}