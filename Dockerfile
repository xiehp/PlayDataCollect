# The FROM instruction sets the Base Image for subsequent instructions.
# Using Nginx as Base Image
FROM maven:3

# The RUN instruction will execute any commands
# Adding HelloWorld page into Nginx server
WORKDIR /playdata

RUN git clone --recursive https://github.com/xiehp/PlayDataCollect.git \
    git pull \
    git submodule update \

WORKDIR PlayDataCollect

RUN mvn clean package \
    cp app/playdatacollect-collector/target/playdatacollect-collector-1.0-SNAPSHOT.jar playdatacollect-collector-1.0.jar
    mvn clean

# The EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime
EXPOSE 13001

# The CMD instruction provides default execution command for an container
# Start Nginx and keep it from running background
CMD ["java", "-jar", "playdatacollect-collector-1.0.jar"]