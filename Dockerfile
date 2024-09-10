FROM alpine as build

ARG MAVEN_VERSION=3.9.9
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries


# Install Java.
RUN apk --update --no-cache add openjdk17 curl git

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

ENV JAVA_HOME /usr/lib/jvm/default-jvm/

RUN git clone https://github.com/clueless-skywatcher/petridish.git /opt/petridish
WORKDIR /opt/petridish
RUN mvn -f pom.xml clean package

COPY target/petridish.jar /usr/local/petridish.jar

CMD ["java", "-jar", "/usr/local/petridish.jar"]
