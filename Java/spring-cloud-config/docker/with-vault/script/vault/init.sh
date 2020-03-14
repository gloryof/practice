#/bin/sh

export VAULT_ADDR="http://127.0.0.1:8200"
CONF_DIR=../../vault/conf
ENV_FILE=conf/envfile
SERVER_PARAM_FILE=../../config-server/param/envfile
APP_PARAM_FILE=../../app-with-vault/param/envfile

# vaultの初期化
vault operator init | grep -e 'Unseal Key' -e 'Initial Root Token' > conf/init_temp
touch ${ENV_FILE}

# vaultのunseal
cat /dev/null > ${ENV_FILE}
cat conf/init_temp | grep 'Unseal Key 1' | awk  '{print "UNSEAL_KEY1="$4}' >> ${ENV_FILE}
cat conf/init_temp | grep 'Unseal Key 2' | awk  '{print "UNSEAL_KEY2="$4}' >> ${ENV_FILE}
cat conf/init_temp | grep 'Unseal Key 3' | awk  '{print "UNSEAL_KEY3="$4}' >> ${ENV_FILE}
cat conf/init_temp | grep 'Unseal Key 4' | awk  '{print "UNSEAL_KEY4="$4}' >> ${ENV_FILE}
cat conf/init_temp | grep 'Unseal Key 5' | awk  '{print "UNSEAL_KEY5="$4}' >> ${ENV_FILE}
cat conf/init_temp | grep 'Initial Root Token' | awk '{print "ROOT_TOKEN="$4}' >> ${ENV_FILE}
rm conf/init_temp

source ${ENV_FILE}

vault operator unseal ${UNSEAL_KEY1}
vault operator unseal ${UNSEAL_KEY2}
vault operator unseal ${UNSEAL_KEY3}

# ポリシーの作成
VAULT_TOKEN=${ROOT_TOKEN} vault policy write admin ${CONF_DIR}/admin-policy.hcl
VAULT_TOKEN=${ROOT_TOKEN} vault policy write manager ${CONF_DIR}/manager-policy.hcl
VAULT_TOKEN=${ROOT_TOKEN} vault policy write java-app ${CONF_DIR}/java-app-policy.hcl

# ユーザ名とパスワードによるログインの設定
VAULT_TOKEN=${ROOT_TOKEN} vault auth enable userpass
VAULT_TOKEN=${ROOT_TOKEN} vault write auth/userpass/users/admin password=admin-password policies=admin
VAULT_TOKEN=${ROOT_TOKEN} vault write auth/userpass/users/manager password=manager-password policies=manager

# AppRoleの設定
VAULT_TOKEN=${ROOT_TOKEN} vault auth enable approle
VAULT_TOKEN=${ROOT_TOKEN} vault write -f auth/approle/role/java-app policies=java-app
VAULT_TOKEN=${ROOT_TOKEN} vault read auth/approle/role/java-app/role-id > conf/role_id
VAULT_TOKEN=${ROOT_TOKEN} vault write -f auth/approle/role/java-app/secret-id > conf/secret_id

cat conf/role_id | grep 'role_id' | awk  '{print "ROLE_ID="$2}' >> ${ENV_FILE}
cat conf/secret_id | grep 'secret_id' | grep -v 'secret_id_' | awk  '{print "SECRET_ID="$2}' >> ${ENV_FILE}

rm conf/role_id
rm conf/secret_id

source ${ENV_FILE}

VAULT_TOKEN=${ROOT_TOKEN} vault write auth/approle/login role_id=${ROLE_ID} secret_id=${SECRET_ID} > conf/app_tokens
cat conf/app_tokens | grep 'token' | grep -v 'token_' | awk  '{print "APP_TOKEN="$2}' >> ${ENV_FILE}

rm conf/app_tokens

source ${ENV_FILE}

# Key/ValueによるSecretの設定
VAULT_TOKEN=${ROOT_TOKEN} vault secrets enable -path=config -version=2 kv

# DatabaseによるDynamic Sercretの設定
VAULT_TOKEN=${ROOT_TOKEN} vault secrets enable -path=database database

# stage-databaseの設定
VAULT_TOKEN=${ROOT_TOKEN} vault write database/config/stage-database \
    plugin_name=postgresql-database-plugin \
    connection_url="postgresql://{{username}}:{{password}}@stage-db:5432/stage-database?sslmode=disable" \
    allowed_roles="stage-db-user" \
    username="postgres" \
    password="password" \
    root_rotation_statements="ALTER USER postgres WITH PASSWORD '{{password}}'"

VAULT_TOKEN=${ROOT_TOKEN} vault write database/roles/stage-db-user \
    db_name=stage-database \
    creation_statements="CREATE ROLE \"{{name}}\" WITH LOGIN PASSWORD '{{password}}' VALID UNTIL '{{expiration}}'; \
        GRANT SELECT ON ALL TABLES IN SCHEMA public TO \"{{name}}\";" \
    default_ttl="1h" \
    max_ttl="24h"
VAULT_TOKEN=${ROOT_TOKEN} vault write -force database/rotate-root/stage-database

# dev-databaseの設定
VAULT_TOKEN=${ROOT_TOKEN} vault write database/config/dev-database \
    plugin_name=postgresql-database-plugin \
    connection_url="postgresql://{{username}}:{{password}}@dev-db:5432/dev-database?sslmode=disable" \
    allowed_roles="dev-db-user" \
    username="postgres" \
    password="password" \
    root_rotation_statements="ALTER USER postgres WITH PASSWORD '{{password}}'"

VAULT_TOKEN=${ROOT_TOKEN} vault write database/roles/dev-db-user \
    db_name=dev-database \
    creation_statements="CREATE ROLE \"{{name}}\" WITH LOGIN PASSWORD '{{password}}' VALID UNTIL '{{expiration}}'; \
        GRANT SELECT ON ALL TABLES IN SCHEMA public TO \"{{name}}\";" \
    default_ttl="1h" \
    max_ttl="24h"
VAULT_TOKEN=${ROOT_TOKEN} vault write -force database/rotate-root/dev-database

touch ${APP_PARAM_FILE}
cat /dev/null > ${APP_PARAM_FILE}
cat ${ENV_FILE} | grep ROLE_ID >> ${APP_PARAM_FILE}
cat ${ENV_FILE} | grep SECRET_ID >> ${APP_PARAM_FILE}

cp ${APP_PARAM_FILE} ${SERVER_PARAM_FILE}