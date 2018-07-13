#!/usr/bin/env bash

docker rm -f xie/playdata-front
docker run -d --name playdata-front -p 13002:13002 xie/playdata-front

docker logs -f playdata-front