# The FROM instruction sets the Base Image for subsequent instructions.
# Using Nginx as Base Image
FROM openjdk:8-jre-alpine
MAINTAINER xie@qq.com

# The RUN instruction will execute any commands
# Adding HelloWorld page into Nginx server
WORKDIR /playdata

COPY playdatacollect-front.jar playdatacollect-front.jar

# The EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime
EXPOSE 13002

# The CMD instruction provides default execution command for an container
# Start Nginx and keep it from running background
CMD ["java", "-jar", "playdatacollect-front.jar"]