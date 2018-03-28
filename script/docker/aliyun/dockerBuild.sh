
echo ${JAVA_HOME}
echo
java -version
echo
mvn --version
echo
ls -l
echo

bash script/docker/aliyun/dockerBuild.sh

# aliyun不会自动下载子项目
git submodule init
git submodule update
mvn package -Dmaven.test.skip=true


echo copy $PWD/playdatacollect/app/playdatacollect-collector/target/playdatacollect-collector.jar
cp $PWD/app/playdatacollect-collector/target/playdatacollect-collector.jar playdatacollect-collector.jar
ls -l
echo

echo back to parent dir
cd ..
ls -l
echo

echo back to parent dir
cd ..
ls -l
echo
