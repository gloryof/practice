#!/bin/bash

export DATA_SOURCE_NAME="postgresql://monitor-user:monitor-pass@mon-pro-db:5432/postgres?sslmode=disable"

postgres_exporter & 

docker-entrypoint.sh postgres

