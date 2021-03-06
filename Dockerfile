# The FROM instruction sets the Base Image for subsequent instructions.
# Using Nginx as Base Image
FROM maven:3-jdk-8
MAINTAINER xie@qq.com

# The RUN instruction will execute any commands
# Adding HelloWorld page into Nginx server
WORKDIR /playdata

RUN rm -rf PlayDataCollect \
    && git clone --recursive https://github.com/xiehp/PlayDataCollect.git \
    #&& git pull \
    #&& git submodule update \
    && pwd

WORKDIR /playdata/PlayDataCollect

RUN mvn clean package \
    && cp app/playdatacollect-collector/target/playdatacollect-collector.jar playdatacollect-collector.jar \
    && mvn clean \
    && pwd

# The EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime
EXPOSE 13001

# The CMD instruction provides default execution command for an container
# Start Nginx and keep it from running background
CMD ["java", "-jar", "playdatacollect-collector.jar"]