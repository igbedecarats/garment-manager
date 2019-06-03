#
# Build
#
FROM openjdk:8-jdk AS build

RUN apt-get update && apt-get -y install maven

WORKDIR /opt/consumer

COPY ./product-consumer/pom.xml /opt/consumer/
RUN mvn install

COPY ./product-consumer/src /opt/consumer/src/
RUN mvn package

#
# Run
#
FROM openjdk:8-jdk

EXPOSE 8090

WORKDIR /opt/consumer

ENV CART_ENDPOINT=cart:8090
ENV DB_HOST=mysql

COPY --from=build /opt/consumer/target/product-consumer-0.0.1-SNAPSHOT.jar consumer.jar

CMD [ "java", "-Xmn256m", "-Xmx768m", "-jar", "-Dspring.profiles.active=local", "consumer.jar" ]
