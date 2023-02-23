#!/bin/zsh

curl -X POST -F file=@./generate-data/result.csv http://localhost:8080/api/io-stats -v
