FROM openjdk:15-jdk-alpine


WORKDIR /root

COPY target/*SNAPSHOT.jar app.jar

RUN apk update
RUN apk add python3
RUN apk add py3-pip
RUN python3 -m pip install --upgrade pip setuptools
RUN python3 -m pip install --upgrade httpie


EXPOSE 8088

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT java -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar app.jar
