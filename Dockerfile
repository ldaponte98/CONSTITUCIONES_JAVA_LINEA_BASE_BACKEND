FROM openjdk:8-alpine
LABEL maintainer="ccb@gmail.com"
WORKDIR /workspace

ENV DOCKERIZE_VERSION v0.6.1

COPY api-cae-1.jar app.jar

#ENV URL="jdbc:mysql://serviciosccbnubedev.c5eqgve9novs.us-east-1.rds.amazonaws.com:3306/CaeDB?zeroDateTimeBehavior=convertToNull&serverTimezone=America/Bogota&useSSL=false"
#ENV USERNAME="uscae_dev"
#ENV PASSWORD="JuB39$K1*2"
#ENV STRATEGY="org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
#ENV RUTA="/workspace/"
#ENV MT="MT"
#ENV ADMIN_MUTA="ADMIN_MUTA"
#ENV KEY="bVV0NGMxb24zcy5jNGU="
RUN apk update && \
    apk add --no-cache tzdata

RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN update-ms-fonts

ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080

#docker build -t api-cae .
#docker run --name api-cae -p 8080:8080 api-cae:latest
#docker container logs  api-cae