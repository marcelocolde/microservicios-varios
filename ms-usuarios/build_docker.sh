#!/bin/bash
./mvnw clean package -DskipTests

echo "Parando docker container si está corriendo"
docker stop usuarios_cont
echo "Borrando container"
docker rm usuarios_cont

echo "Borrando imagen"
docker rmi usuarios
echo "Construyendo imagen"
docker build -t usuarios .
docker run --name usuarios_cont -p 8001:8001 -d usuarios