#!/bin/sh

zap_option=""

# Auth Script
zap_option="${zap_option} -config script.scripts\(0\).name='Auth' "
zap_option="${zap_option} -config script.scripts\(0\).engine='ECMAScript'"
zap_option="${zap_option} -config script.scripts\(0\).type=Authentication"
zap_option="${zap_option} -config script.scripts\(0\).enabled=true"
zap_option="${zap_option} -config script.scripts\(0\).file='/zap/wrk/Auth.js'"

# Session Script
zap_option="${zap_option} -config script.scripts\(1\).name='TokenSession' "
zap_option="${zap_option} -config script.scripts\(1\).engine='ECMAScript'"
zap_option="${zap_option} -config script.scripts\(1\).type='Session Management'"
zap_option="${zap_option} -config script.scripts\(1\).enabled=true"
zap_option="${zap_option} -config script.scripts\(1\).file='/zap/wrk/TokenSession.js'"

zap-api-scan.py \
  -t http://target:8080/v3/api-docs \
  -f openapi  \
  -n /zap/wrk/target.context  \
  -I \
  -U test-user \
  -d \
  -z "${zap_option}"

cp /home/zap/.ZAP/config.xml /zap/wrk/