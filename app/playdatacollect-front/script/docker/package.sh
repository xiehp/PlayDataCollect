#!/usr/bin/env bash

export MAVEN_OPTS="-Xmx512m"
mvn clean package -Dmaven.test.skip
cd ..
cd ..

mkdir -p src

cp packagePlayDataCollect/PlayDataCollect/app/playdatacollect-front/target/playdatacollect-front.jar src/playdatacollect-front.jar

pwd
