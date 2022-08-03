#!/bin/zsh

kubectl delete --recursive -f app
kubectl delete --recursive -f web
kubectl delete --recursive -f zipkin
kubectl delete --recursive -f chaos-mesh
