#!/usr/bin/env bash

docker run -d --name playdata-front -p 13002:13002 xie/playdata-front

docker logs -f playdata-front