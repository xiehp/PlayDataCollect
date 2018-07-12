#!/usr/bin/env bash

sh package.sh
sh dockerBuild.sh
sh dockerRun.sh