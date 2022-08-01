#!/bin/sh
helm uninstall chaos-mesh -n chaos-manager

kubectl delete -f namespace.yaml