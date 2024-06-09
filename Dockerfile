# Etapa de build
FROM ubuntu:latest AS build

# Atualiza os pacotes e instala o JDK 17 e o Maven
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Define o diretório de trabalho
WORKDIR /app

# Copia o código-fonte para a imagem
COPY . .

# Compila o projeto e gera o arquivo JAR
RUN mvn clean install

# Etapa final
FROM openjdk:17-jdk-slim

# Expõe a porta 8080
EXPOSE 8080

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build para a imagem final
COPY --from=build /app/target/demo-1.0.0.jar app.jar

# Define o ponto de entrada e ativa o perfil 'prod'
ENTRYPOINT [ "java", "-jar", "app.jar", "--spring.profiles.active=prod" ]
