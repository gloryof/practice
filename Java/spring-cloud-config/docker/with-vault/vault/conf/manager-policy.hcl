
path "database/*"
{
  capabilities = ["read", "list"]
}

path "config/*"
{
  capabilities = ["create", "read", "update", "delete", "list"]
}