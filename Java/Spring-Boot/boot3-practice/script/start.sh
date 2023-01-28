#!/bin/zsh

kubectl apply -f k8s/zipkin --recursive
kubectl apply -f k8s/loki --recursive
kubectl apply -f k8s/grafana --recursive
