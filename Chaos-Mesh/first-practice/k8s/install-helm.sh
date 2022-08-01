#!/bin/sh
kubectl apply -f namespace.yaml
helm install chaos-mesh chaos-mesh/chaos-mesh --version 2.3.0 -n chaos-manager --set controllerManager.enableFilterNamespace=true
