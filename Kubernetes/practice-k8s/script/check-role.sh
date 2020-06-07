#! /bin/sh

kubectl exec $1 -- redis-cli INFO | grep role
