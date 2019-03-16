FROM java:8-jdk-alpine
VOLUME /tmp
COPY ./target/neuedaassignment-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch neuedaassignment-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","neuedaassignment-0.0.1-SNAPSHOT.jar"]  