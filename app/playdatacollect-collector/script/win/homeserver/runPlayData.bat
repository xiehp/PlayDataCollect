
echo %~dp0

cd %~dp0

echo set the java home path
set "app_java_home_path=D:\work\java\X64\jdk1.8.0_131"
:: set "app_java_home_path=D:\work\java\X64\jdk-9.0.1"


set "app_java_exe=%app_java_home_path%\bin\java"
set "app_java_ext_lib_path=%app_java_home_path%\jre\lib\ext"
:: set "JAVA_OPTION= -Xms200M -Xmx250M -XX:MetaspaceSize=100M -XX:MaxMetaspaceSize=150M -XX:CompressedClassSpaceSize=64M "


%app_java_exe% -version

:: 不再需要该参数-Dlogback.configurationFile=logback.xml 
%app_java_exe% -jar playdatacollect-collector.jar

pause


