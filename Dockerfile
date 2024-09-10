FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
RUN apk --update add git
RUN git clone https://github.com/clueless-skywatcher/petridish.git /opt/petridish
WORKDIR /opt/petridish
COPY src /usr/petridish/src
COPY pom.xml /usr/petridish
RUN mvn -f /usr/petridish/pom.xml clean package

FROM openjdk:17-jdk-alpine
COPY --from=build /usr/petridish/target/petridish.jar /usr/local/lib/petridish.jar
EXPOSE 6379
ENTRYPOINT ["java","-jar","/usr/local/lib/petridish.jar"]