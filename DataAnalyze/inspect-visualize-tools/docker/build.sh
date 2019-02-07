#!/bin/sh

docker-compose build

docker-compose start redash-data
docker-compose run redash-server ./manage.py database create_tables

if [ $? -ne 0 ]; then 
    echo "Build failed"
    exit 1
fi

docker-compose stop

echo "Build complete"