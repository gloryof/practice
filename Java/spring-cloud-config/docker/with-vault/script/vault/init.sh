#/bin/sh

VAULT_ADDR="http://127.0.0.1:8200"
CONF_DIR=../../vault/conf

vault operator init | grep -e 'Unseal Key' -e 'Initial Root Token' > conf/init_temp

ENV_FILE=conf/envfile
touch ${ENV_FILE}

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

VAULT_TOKEN=${ROOT_TOKEN} vault policy write admin ${CONF_DIR}/admin-policy.hcl
VAULT_TOKEN=${ROOT_TOKEN} vault policy write manager ${CONF_DIR}/manager-policy.hcl
VAULT_TOKEN=${ROOT_TOKEN} vault policy write java-app ${CONF_DIR}/java-app-policy.hcl

VAULT_TOKEN=${ROOT_TOKEN} vault auth enable userpass
VAULT_TOKEN=${ROOT_TOKEN} vault write auth/userpass/users/admin password=admin-password policies=admin
VAULT_TOKEN=${ROOT_TOKEN} vault write auth/userpass/users/manager password=manager-password policies=manager

VAULT_TOKEN=${ROOT_TOKEN} vault auth enable approle
VAULT_TOKEN=${ROOT_TOKEN} vault write -f auth/approle/role/java-app policies=java-approle
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