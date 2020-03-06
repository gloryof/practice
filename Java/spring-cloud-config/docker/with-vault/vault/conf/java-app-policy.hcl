
path "database/*"
{
  capabilities = ["read", "list"]
}

path "config/*"
{
  capabilities = ["read", "list"]
}

path "secret/config_app"
{
  capabilities = ["read", "list"]
}

path "secret/application"
{
  capabilities = ["read", "list"]
}

path "sys/revoke" 
{
  capabilities = ["update"]
}
