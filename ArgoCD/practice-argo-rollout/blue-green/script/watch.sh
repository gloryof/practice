#!/bin/sh

kubectl argo rollouts get rollout blue-green-app -n practice -w