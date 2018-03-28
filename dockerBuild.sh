
echo export JAVA_HOME
java -version
mvn --version

docker build -t xie/playdata --force-rm --no-cache .

pause