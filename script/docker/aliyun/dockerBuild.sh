
echo export JAVA_HOME
java -version

git clone --recursive https://github.com/xiehp/PlayDataCollect.git
cd PlayDataCollect
git submodule update
mvn clean package


echo 复制文件 $PWD/playdatacollect/app/playdatacollect-collector/target/playdatacollect-collector.jar
cp $PWD/app/playdatacollect-collector/target/playdatacollect-collector.jar ../playdatacollect-collector.jar

echo 回到先前目录
cd ..

::git clone https://github.com/xiehp/PlayDataCollect.git
::cd PlayDataCollect
::git submodule init
::git submodule update
::cd common
::git checkout SplitProject

pause