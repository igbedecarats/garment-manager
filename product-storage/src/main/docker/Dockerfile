FROM openjdk:8-jdk-alpine
VOLUME /tmp
VOLUME /var/log
ENV LANG en_US.UTF-8
RUN apk --update add fontconfig ttf-dejavu && rm -rf /var/cache/apk/*
ADD @project.build.finalName@.jar app.jar
EXPOSE 8091
ENTRYPOINT exec java -Xmn256m -Xmx768m -Duser.timezone=UTC -Djava.security.egd=file:/dev/./urandom -jar /app.jar
