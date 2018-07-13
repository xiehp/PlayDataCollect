#!/usr/bin/env bash

export MAVEN_OPTS="-Xmx512m"

pwd
mkdir -p packagePlayDataCollect
cd packagePlayDataCollect
pwd

if [ -d "PlayDataCollect" ]; then
    cd PlayDataCollect
    git pull
    git submodule update
else
    git clone --recursive ssh://git@gogs.acgimage.cn:53022/xie/PlayDataCollect.git
    cd PlayDataCollect
fi

pwd
mvn clean package -Dmaven.test.skip
cd ..
cd ..

mkdir -p src

cp packagePlayDataCollect/PlayDataCollect/app/playdatacollect-front/target/playdatacollect-front.jar src/playdatacollect-front.jar

pwd
