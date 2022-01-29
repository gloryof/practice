# !/bin/sh
source ./local-file/local.env
drone exec --pipeline integration-test --env-file ./local-file/local.env --trusted