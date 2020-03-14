
path "database/*"
{
  capabilities = ["read", "list"]
}

path "database/creds/dev-db-user"
{
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "database/creds/dev-db-user"
{
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "config/data/*"
{
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "sys/revoke" 
{
  capabilities = ["update"]
}
