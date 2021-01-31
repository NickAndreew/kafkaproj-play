# KafkaProj-Play Application


This application has been designed for demonstration and modeling purposes. 

It is recommended to upgrade every integration module with frameworks, 
that may provide better performance and scalability of application functionalities. 


# Stack


It is using the most basic tools for working with the technology stack required - MongoDB, Kafka, Java, Docker.

This application is using Basic Java 12 HTTP Server Implementation
in order to make Admin access possible over TCP/HTTP protocol for any kind of control operations. 

Kafka Messaging Module provides consuming and producing capabilities to the application, 
that may be customized and scaled.

MongoDb database is backing application storage, providing CRUD operations to Project instances.

Everything is packed into Docker images that run together in separate containers and communicate with each other.


# Requirements

In order to deploy the application there are few requirements that have to be fulfilled:

 * Java 12 installed - https://www.oracle.com/java/technologies/javase/jdk12-archive-downloads.html
 * Gradle Package Manger installed - https://gradle.org/install/;
 * Docker Containers Service installed - https://docs.docker.com/get-docker/;


# Recommended

You might find these tools useful:

 * KafkaTool - https://www.kafkatool.com/
 * Postman - https://www.postman.com/downloads/
 * Intellij IDEA - https://www.jetbrains.com/idea/download/#section=windows


# HTTP Server - Admin Controller

Application currently has just one HTTP endpoint - '/app/admin', 
listening for requests on port - 8080, at localhost.

There are just two HTTP methods listening on the Application.

1. GET Request - as simple as possible, Application running status check

```
GET /app/admin HTTP/1.1
Host: localhost:8080
cache-control: no-cache
Postman-Token: 627667c9-bfb2-4a26-9eb3-a3cae7ff48d4
```

2. POST Request accepts JSON body in the format specified by Java objects, see example below.

```
POST /app/admin HTTP/1.1
Host: localhost:8080
Content-Type: application/json
{
    "param1":"3",
    "param2":"Title",
    "param3":"Context Message",
    "param4":"Context Message",
    "param5":"Context Message"
}
```

Application is currently configured to send Kafka Message to the specified in configurations topic, 
on the HTTP POST request, with request body formatted to the inner application entity.


# Kafka

Application implements Consumer and Producer Instances for demonstration and modeling purposes. 

Kafka message entity format: 

```
{
    "param1":"3",
    "param2":"Title",
    "param3":"Context Message",
    "param4":"Context Message",
    "param5":"Context Message"
}
```


# Mongo Db

Application is configured to save all the messages that are consumed in the KafkaConsumer to MongoDB Database.
This module has to be finished, very raw implementation, connection and unsafe operations, need more time.


# Deployment

The project is still on development stage and has to be completed.

In order to deploy the application the following commands have to be executed: 
 
``` 
 1. pull or download the project
 1. start docker
 1. cd ./projectDir
 1. gradle build - if command fails from console (java/gradle versions mismatch/incompatible with project resources 
 or other issues), please try building it in Intellij IDEA, 
 you can adjust gradle settings on the project
 to make it work with necessary versions.
 1. cd ./projectDir/docker
 1. docker-compose up
``` 

 After running these commands, if all goes well and all dependencies and versions matched, 
 the application should start pulling required images and start all services on user host machine.  
 

# Current Issues

 - [ ] Kafka Producer does not send the message - drops with 'org.apache.kafka.common.errors.TimeoutException', 
 however, it can be a lack of resources on my personal PC, 
 all 8 GB of RAM got occupied and some other programs on my laptop has hanged, 
 Kafka Tool almost stopped responding, got very slow and didn't load data, 
 so it might be just my local issue, and you might not experience these problems on a more powerful machine.
 
 - [ ] Database has not been tested yet but it should be working. 
 
 - [ ] Dependency Injection should be rewritten, optimized. Currently, it's just a bear minimum of java boilerplate code, 
 to run the stack of technologies required. 
 
 - [ ] No frameworks have been used in this implementation, apart from Database Client drivers, Kafka Client Libraries, 
 and some Java libraries used for logging and working with text. Usually frameworks provide better, 
 more elegant libraries with a lot of functionalities embedded in their core, 
 it would be preferrable to refactor everything to work with some modern frameworks.
 
 - [ ] Confgurations mechanism is not finished, it has to be moved to some specific place - file, database or docker-compose.yml, etc. 
 
 - [ ] Logging can be configured to store logs to console, file or Log Database (Elasticsearch, Kibana, etc.)
 
 - [ ] Metrics can be added, there are no metrics configured on the system right now. (Prometheus, Grafana, etc.)
 