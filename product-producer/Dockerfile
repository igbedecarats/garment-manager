#
# Build
#
FROM openjdk:8-jdk AS build

RUN apt-get update && apt-get -y install maven

WORKDIR /opt/producer

COPY ./product-producer/pom.xml /opt/producer/
RUN mvn install

COPY ./product-producer/src /opt/producer/src/
RUN mvn package

#
# Run
#
FROM openjdk:8-jdk

EXPOSE 8090

WORKDIR /opt/producer

ENV CART_ENDPOINT=cart:8090
ENV DB_HOST=mysql

COPY --from=build /opt/producer/target/product-producer-0.0.1-SNAPSHOT.jar producer.jar

CMD [ "java", "-Xmn256m", "-Xmx768m", "-jar", "producer.jar" ]
