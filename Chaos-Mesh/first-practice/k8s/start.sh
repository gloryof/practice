#!/bin/sh
kubectl apply -f namespace.yaml
kubectl apply --recursive -f web
kubectl apply --recursive -f app
kubectl apply --recursive -f zipkin
kubectl apply --recursive -f chaos-mesh
