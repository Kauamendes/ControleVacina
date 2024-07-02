FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk maven postgresql-client

WORKDIR /app

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y postgresql

EXPOSE 8080 5432

WORKDIR /app

COPY --from=build /app/target/demo-1.0.0.jar app.jar

RUN service postgresql start && \
    sudo -u postgres psql -c "CREATE USER cad_vacinas_senac_user WITH ENCRYPTED PASSWORD 'NN0ZJ14TXeNj3lWvAXhqjW8I5vjmzMj2';" && \
    sudo -u postgres psql -c "CREATE DATABASE cadvacinas WITH OWNER cad_vacinas_senac_user;"

ENTRYPOINT service postgresql start && \
           java -jar app.jar --spring.profiles.active=prod
