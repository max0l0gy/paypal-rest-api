####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the docker image run:
#
# mvn package
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile.jvm -t quarkus/paypal-rest-api-jvm .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/paypal-rest-api
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5050
# 
# Then run the container using : 
#
# docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" quarkus/paypal-rest-api-jvm
#
###
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
MAINTAINER  Maxim Morev <maxmorev@gmail.com>

ARG JAVA_PACKAGE=java-11-openjdk-headless
ARG RUN_JAVA_VERSION=1.3.8

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

# Install java and the run-java script
# Also set up permissions for user `1001`
RUN apk --no-cache add curl \
    && mkdir /deployments \
    && chown 1001 /deployments \
    && chmod "g+rwX" /deployments \
    && chown 1001:root /deployments \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
WORKDIR /deployments
COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar

EXPOSE 8080
USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]