#!/usr/bin/env bash

# put this file to a docker deploy folder,
# and the other script files will first copy to the docker deploy folder
pwd
mkdir -p packagePlayDataCollect
cd packagePlayDataCollect
pwd

if [ -d "PlayDataCollect" ]; then
    cd PlayDataCollect
    git pull
    git submodule update
    cd ..
else
    git clone --recursive ssh://git@gogs.acgimage.cn:53022/xie/PlayDataCollect.git
fi

cd ..


# copy script file
cp -r packagePlayDataCollect/PlayDataCollect/app/playdatacollect-front/script/docker/* .

sh rePackageAndRun.sh
