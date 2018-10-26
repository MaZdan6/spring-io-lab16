FROM openjdk:8u141-jre
MAINTAINER Darek Kaczynski <dariusz.kaczynski@sages.com>

RUN mkdir -p /opt/config

# App
COPY bin/run.sh /opt/run.sh
COPY @project.build.finalName@.jar /opt/app.jar

# Ports
EXPOSE 8080

# Volumes
VOLUME /opt/config/

# Running
WORKDIR /opt
ENTRYPOINT "./run.sh"
