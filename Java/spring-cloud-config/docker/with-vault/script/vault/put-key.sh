# /bin/sh

export VAULT_ADDR="http://127.0.0.1:8200"
ENV_FILE=conf/envfile

source ${ENV_FILE}

# config.vault.valueの設定
VAULT_TOKEN=${ROOT_TOKEN} vault kv put config/config_app,dev config.vault.value="dev vault vaule"
VAULT_TOKEN=${ROOT_TOKEN} vault kv put config/config_app,stage config.vault.value="stage vault vaule"
VAULT_TOKEN=${ROOT_TOKEN} vault kv put config/config_app,static config.vault.value="static vault vaule"

# staticユーザ用のDB設定
VAULT_TOKEN=${ROOT_TOKEN} vault kv patch config/config_app,static spring.datasource.username=static-user
VAULT_TOKEN=${ROOT_TOKEN} vault kv patch config/config_app,static spring.datasource.password=static-password