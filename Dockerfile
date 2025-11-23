# ----------------------------------------------------------------------------
# BUILD STAGE: Compila a aplicação Java
# Baseada em Alpine Linux, conforme o exercício.
# ----------------------------------------------------------------------------
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

# CORREÇÃO CRÍTICA DO ERRO SSL/HANDSHAKE (Alpine Linux):
# Garante que os certificados de segurança (ca-certificates) estejam atualizados.
RUN apk add --update ca-certificates && rm -rf /var/cache/apk/*

# 1. Define o diretório de trabalho padrão
WORKDIR /app

# Copia e baixa as dependências (para cache e build mais rápido)
COPY pom.xml .
RUN mvn dependency:go-offline || echo "Ignorando erro de dependência para cache."

# 2. Copia o código-fonte
COPY . .
# 3. Realiza o Build da Aplicação com o Maven
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------------
# RUN STAGE: Cria a imagem de execução leve
# ----------------------------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

# 1. Cria e usa o usuário sem privilégios 'userapp'
RUN adduser -h /home/userapp -s /bin/sh -D userapp
USER userapp

# 2. Define o diretório de trabalho
WORKDIR /app

# 3. Copia apenas o JAR do Build Stage (NOME AJUSTADO)
COPY --from=build /app/target/one-matter-api-0.0.1-SNAPSHOT.jar app.jar

# 4. Expõe a porta 8080
EXPOSE 8080

# 5. Comando de execução da aplicação
CMD ["java", "-jar", "app.jar"]