#!/bin/sh

kubectl argo rollouts get rollout nginx-deployment -n practice -w