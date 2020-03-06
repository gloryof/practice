# This configuration is copied from below site.
# https://learn.hashicorp.com/vault/identity-access-management/iam-policies

path "auth/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}

path "sys/auth/*"
{
  capabilities = ["create", "update", "delete", "sudo"]
}

path "sys/auth"
{
  capabilities = ["read"]
}

path "sys/policies/acl/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}

path "sys/policies/acl"
{
  capabilities = ["list"]
}

path "secret/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}

path "sys/mounts/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}

path "sys/health"
{
  capabilities = ["read", "sudo"]
}

path "sys/capabilities"
{
  capabilities = ["create", "update"]
}

path "sys/capabilities-self"
{
  capabilities = ["create", "update"]
}

# Edited below
path "database/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}

path "approle/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}

path "userpass/*"
{
  capabilities = ["create", "read", "update", "delete", "list", "sudo"]
}
