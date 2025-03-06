#!/bin/bash

FROM openjdk:17.0.2

WORKDIR /app

COPY ./ms-usuarios/target/ms-usuarios-0.0.1-SNAPSHOT.jar /app
# o COPY ./target/ms-usuarios-0.0.1-SNAPSHOT.jar .

RUN mkdir ./logs

EXPOSE 8001

# ENTRYPOINT [ "java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]
CMD [ "java", "-jar", "ms-usuarios-0.0.1-SNAPSHOT.jar"]

