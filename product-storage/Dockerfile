#
# Build
#
FROM openjdk:8-jdk AS build

RUN apt-get update && apt-get -y install maven

WORKDIR /opt/storage

COPY ./product-storage/pom.xml /opt/storage/
RUN mvn install

COPY ./product-storage/src /opt/storage/src/
RUN mvn package

#
# Run
#
FROM openjdk:8-jdk

EXPOSE 8090

WORKDIR /opt/storage

ENV CART_ENDPOINT=cart:8090
ENV DB_HOST=mysql

COPY --from=build /opt/storage/target/product-storage-0.0.1-SNAPSHOT.jar storage.jar

CMD [ "java", "-Xmn256m", "-Xmx768m", "-jar", "storage.jar" ]
