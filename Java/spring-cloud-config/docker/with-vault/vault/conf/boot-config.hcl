storage "file" {
   path = "/vault/file"
}

listener "tcp" {
  address     = "127.0.0.1:8200"
  tls_disable = 1
}

listener "tcp" {
  address     = "vault:8200"
  tls_disable = 1
}

ui = true
disable_mlock = true