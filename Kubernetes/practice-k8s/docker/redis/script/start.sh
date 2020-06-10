#! /bin/sh

MASTER_POD=${STATEFUL_SET_NAME}-0
MASTER_HOST="${MASTER_POD}.${SERVICE_NAME}.default.svc.cluster.local"

if [ $HOSTNAME = ${MASTER_POD} ]; then
  redis-server --port 6379
else
  redis-server --replicaof ${MASTER_HOST} 6379
fi