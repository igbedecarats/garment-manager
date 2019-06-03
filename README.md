# garment-manager

## General purpose

Garment Manager is a project set up composed of different slices, each with it's own responsibility. The idea behind them is to have a base model for having a service that produces a resource that's published to a queue which later on is stored in a database so that it can be fetched later on by another service via http requests.
With its services, clients can save and ask for different pieces of garment that are modeled as *Products* in the business model.

## Stack

The backend services responsible for handling the Rest APIs are build with Java 8 using Spring Boot and different starters such as JPA, RabbitMQ and Actuator. Finally, the selected queue messaing is RabbitMQ and the database is MySQL. All of them are built in docker images that are configured in a docker-compose.yml file so that can start up all together at once. The Rest APIs are documented using Swagger. 

## Prerequisites
 * docker
 * docker-compose

This are the only requirements because each of the services docker images downloads mvn and compiles the corresponding java projects at build time, so that this project can be run in any machine (as long as docker and docker-compose are installed there).

## Set up

### Product Producer Service (aka Write API)

This Java 8 application built with Spring Boot that handles a single Rest API: `POST /api/products`. The contract for it can be checked at the [Service Swagger Page](http://localhost:8090/producer/swagger-ui.html#) once the project is up with docker-compose. Once the request is handled, its payload is published to the *Products Queue*.

### Queue

RabbitMQ was chosen for the messagin message queue. The docker image used is rabbitmq:3-management which adds RabbitMQ management console that is exposed on port 15673 (it can be accesed [here](http://localhost:15673/#/) with the default user **guest** and password **guest**). 

```
rabbitmq-container:
  image: rabbitmq:3-management
  ports:
    - 5672:5672
    - 15672:15672
  volumes:
    - ./rabbitmq/definitions.json:/etc/rabbitmq/definitions.json:ro
    - ./rabbitmq/rabbitmq.config:/etc/rabbitmq/rabbitmq.config:ro
```

For this set up, a queue and exchange are already pre-configured in the RabbitMQ server with the definitions and configurations defined in the *rabbitmq* folder.

### Product Storage Service (aka Persistance Service)

This Java 8 application built with Spring Boot listens to _Product_ messages published in the queue and stores them in the databse with the proper business model.

### Database (aka RMDBS)

An official MySQL docker image was chosen for the database of the _Product_ model, which is initialized upon start up with the .sql scripts located in the **mysql** folder.

### Product Consumer Service (aka Read API)

This Java 8 application built with Spring Boot that handles APIs for fetching _Products_: one for single products `GET /api/products/:identification`, and another one for all of them `GET /api/products`. The contract for them can be checked at the [Service Swagger Page](http://localhost:8092/consumer/swagger-ui.html#) once the project is up with docker-compose.

### Product model
```
{
   "id":"CG7088",
   "name":"Nite Jogger Shoes",
   "model_number":"BTO93",
   "product_type":"inline",
   "meta_data":{
      "page_title":"adidas Nite Jogger Shoes - Black | adidas UK",
      "site_name":"adidas United Kingdom",
      "description":"Shop for Nite Jogger Shoes - Black at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - Black at the official adidas UK online store.",
      "keywords":"Nite Jogger Shoes",
      "canonical":"//www.adidas.co.uk/nite-jogger-shoes/CG7088.html"
   },
   "pricing_information":{
      "standard_price":119.95,
      "standard_price_no_vat":99.96,
      "currentPrice":119.95
   },
   "product_description":{
      "title":"Nite Jogger Shoes",
      "subtitle":"Modern cushioning updates this flashy '80s standout.",
      "text":"Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."
   }
}
```

### _Notes_

A docker-compose.yaml file version 2.x was used for the need of having dependency condition checking before staring the Persistence Service and Read API since they depend on having the database running with the expected schema DDL. (the *condition* feature was removed from docker-compose 3.x https://docs.docker.com/compose/compose-file/#depends_on)

## Running the project
If the requirements are met, then all you have to do is run `docker-compose up` on the base dir of this project. Once all the containers are up, you can check their status with `docker-compose ps` and you'll get
```
         Name                        Command                  State                                                 Ports
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
gm_consumer-container_1   java -Xmn256m -Xmx768m -ja ...   Up             8090/tcp, 0.0.0.0:8092->8092/tcp
gm_mysql-container_1      docker-entrypoint.sh mysqld      Up (healthy)   0.0.0.0:3307->3306/tcp, 33060/tcp
gm_producer-container_1   java -Xmn256m -Xmx768m -ja ...   Up             0.0.0.0:8090->8090/tcp
gm_rabbitmq-container_1   docker-entrypoint.sh rabbi ...   Up (healthy)   15671/tcp, 0.0.0.0:15673->15672/tcp, 25672/tcp, 4369/tcp, 5671/tcp, 0.0.0.0:5673->5672/tcp
gm_storage-container_1    java -Xmn256m -Xmx768m -ja ...   Up             8090/tcp, 0.0.0.0:8091->8091/tcp
```
## Troubleshooting

Usefulf docker-compose and docker commands to run in different scenarios:
* `docker-compose down` -> Stops and removes containers, networks, images, and volumes. Run this when one container fails and you want to start from scratch.
* `docker rmi X`-> Removes any container. Useful if you want to remove any generated by this project, such as gm_storage-container_1, gm_consumer-container_1, etc.
* `docker-compose up --build` -> Builds the images before staring the containers. Useful if you change something in the base code of any of the java project and you want to generate the docker image again.

## Testing the project

Each of the java projects has a set of unit tests and integration tests using [test constainers](https://www.testcontainers.org/). To run the tests for each of them, go to the desired project folder and run `mvn clean install`. Take into consideration that these tests are run when each of the docker images is built with docker-compose.

Once the project is up with docker-compose, you can send requests to the Write API using its Swagger at http://localhost:8090/producer/swagger-ui.html# after startup. The same can be done for the Read API at http://localhost:8092/consumer/swagger-ui.html#.

If you want to test the records from the initialization .sql scripts you can run these curls:

### Getting a product by its id
```
curl -X GET "http://localhost:8092/consumer/api/products/BD7933" -H "accept: application/json;charset=UTF-8"
```
which should return
```
{"id":"BD7933","name":"Nite Jogger Shoes","model_number":"inline","product_type":"BTO93","meta_data":{"page_title":"adidas Nite Jogger Shoes - Black | adidas UK","site_name":"adidas United Kingdom","description":"Shop for Nite Jogger Shoes - Black at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - Black at the official adidas UK online store.","keywords":"Nite Jogger Shoes","canonical":"//www.adidas.co.uk/nite-jogger-shoes/BD7933.html"},"pricing_information":{"standard_price":109.95,"standard_price_no_vat":91.62,"currentPrice":109.95},"product_description":{"title":"Nite Jogger Shoes","subtitle":"Modern cushioning updates this flashy '80s standout.","text":"Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."}}
```

### Getting all the products
```
curl -X GET "http://localhost:8092/consumer/api/products/" -H "accept: application/json;charset=UTF-8"   
```
which should return
```
[{"id":"BD7676","name":"Nite Jogger Shoes","model_number":"inline","product_type":"BTO93","meta_data":{"page_title":"adidas Nite Jogger Shoes - White | adidas UK","site_name":"adidas United Kingdom","description":"Shop for Nite Jogger Shoes - White at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - White at the official adidas UK online store.","keywords":"Nite Jogger Shoes","canonical":"//www.adidas.co.uk/nite-jogger-shoes/BD7676.html"},"pricing_information":{"standard_price":99.95,"standard_price_no_vat":83.29,"currentPrice":99.95},"product_description":{"title":"Nite Jogger Shoes","subtitle":"Modern cushioning updates this flashy '80s standout.","text":"Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."}},{"id":"BD7933","name":"Nite Jogger Shoes","model_number":"inline","product_type":"BTO93","meta_data":{"page_title":"adidas Nite Jogger Shoes - Black | adidas UK","site_name":"adidas United Kingdom","description":"Shop for Nite Jogger Shoes - Black at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - Black at the official adidas UK online store.","keywords":"Nite Jogger Shoes","canonical":"//www.adidas.co.uk/nite-jogger-shoes/BD7933.html"},"pricing_information":{"standard_price":109.95,"standard_price_no_vat":91.62,"currentPrice":109.95},"product_description":{"title":"Nite Jogger Shoes","subtitle":"Modern cushioning updates this flashy '80s standout.","text":"Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."}},{"id":"F33837","name":"Nite Jogger Shoes","model_number":"inline","product_type":"BTT86","meta_data":{"page_title":"adidas Nite Jogger Shoes - Green | adidas UK","site_name":"adidas United Kingdom","description":"Shop for Nite Jogger Shoes - Green at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - Green at the official adidas UK online store.","keywords":"Nite Jogger Shoes","canonical":"//www.adidas.co.uk/nite-jogger-shoes/F33837.html"},"pricing_information":{"standard_price":99.95,"standard_price_no_vat":83.29,"currentPrice":99.95},"product_description":{"title":"Nite Jogger Shoes","subtitle":"Modern cushioning updates this flashy '80s standout.","text":"Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and ripstop nylon upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."}}]
```

### Storing a new product
```
curl 'http://localhost:8090/producer/api/products' -H 'Content-Type: application/json' -H 'accept: application/json;charset=UTF-8' --data-binary $'{\n   "id":"CG7088",\n   "name":"Nite Jogger Shoes",\n   "model_number":"BTO93",\n   "product_type":"inline",\n   "meta_data":{\n      "page_title":"adidas Nite Jogger Shoes - Black | adidas UK",\n      "site_name":"adidas United Kingdom",\n      "description":"Shop for Nite Jogger Shoes - Black at adidas.co.uk\u21 See all the styles and colours of Nite Jogger Shoes - Black at the official adidas UK online store.",\n      "keywords":"Nite Jogger Shoes",\n      "canonical":"//www.adidas.co.uk/nite-jogger-shoes/CG7088.html"\n   },\n   "pricing_information":{\n      "standard_price":119.95,\n      "standard_price_no_vat":99.96,\n      "currentPrice":119.95\n   },\n   "product_description":{\n      "title":"Nite Jogger Shoes",\n      "subtitle":"Modern cushioning updates this flashy \'80s standout.",\n      "text":"Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."\n   }\n}'
```

## Securing the project

An **.env** can be added to the root of the project, where the docker-compose.yaml is located, to include environment variables for the sensible configurations like the rabbitmq user and password, as well as for the MySQL user and password.

Additionally, the Write API can be secured by using, for example, [JWT](https://jwt.io/) tokens which can be sent in the requests in the HTTP Header _Authorization_ with the value _Bearer jwt-token_. The request can be validated by adding a Spring Security filter that validates the token using the [jwt-java](https://github.com/auth0/java-jwt) library with a signature that can be also be set as an environment varible in the before mentioned .env file. 

## Optimizing the project

Cache can be added in the Read API service to handle high load and provide low latency read operations. Also, to avoid [select n+1 problems](https://bojanv91.github.io/posts/2018/06/select-n-1-problem), the select query can be optimized by, for example, replacing the usage of JPARepository methods (like findByx) with an sql query that can be run with JDBC.
