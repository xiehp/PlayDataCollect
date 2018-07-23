
echo ʹ��jdk8����maven����
echo export JAVA_HOME=/usr/lib/jvm/java-8-oracle/
:: export JAVA_HOME=/usr/lib/jvm/java-8-oracle/
java -version


git clone --recursive https://github.com/xiehp/PlayDataCollect.git
cd PlayDataCollect
git pull --rebase=true
git submodule update
call mvn clean package

echo �����ļ� $PWD/playdatacollect/app/playdatacollect-collector/target/playdatacollect-collector.jar
copy /y app\playdatacollect-collector\target\playdatacollect-collector.jar ..\playdatacollect-collector.jar


echo �ص���ǰĿ¼
cd ..

::git clone https://github.com/xiehp/PlayDataCollect.git
::cd PlayDataCollect
::git submodule init
::git submodule update
::cd common
::git checkout SplitProject
