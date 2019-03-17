# Start with a base image containing Java runtime
FROM java:8-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

# Add Maintainer Info
LABEL maintainer="nitingakhar@yahoo.com"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/neuedaassignment-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} neuedaassignment-demo.jar

ENTRYPOINT ["java","-jar","neuedaassignment-demo.jar"]  
