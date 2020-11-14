#! /bin/sh
kubectl apply -f namespace.yml
kubectl apply -f . -R