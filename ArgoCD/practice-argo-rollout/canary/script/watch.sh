#!/bin/sh

kubectl argo rollouts get rollout canary-app -n practice -w