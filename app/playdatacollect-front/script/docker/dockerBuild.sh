#!/usr/bin/env bash

cp Dockerfile src/
docker build -t xie/playdata-front --force-rm --no-cache src

