
path "database/*"
{
  capabilities = ["read", "list"]
}

path "config/*"
{
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "secret/*"
{
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "database/*"
{
  capabilities = ["create", "read", "update", "delete", "list", ]
}

path "sys/revoke" 
{
  capabilities = ["create", "read", "update", "delete", "list", ]
}