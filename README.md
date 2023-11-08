docker run -p 80:80 -p 443:443 --name nginx --restart always \
-v   /data/nginx/html:/usr/share/nginx/html \
-v   /data/nginx/logs:/var/log/nginx \
-v   /data/nginx/conf:/etc/nginx \
-d nginx:latest


docker run  -p 3306:3306 -v /data/mysql:/var/lib/mysql  -e MYSQL_ROOT_PASSWORD=admin -d mysql:5.7


# Spring and Spring Boot 相关文档

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)
* [Spring Session](https://docs.spring.io/spring-session/reference/)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#data.nosql.redis)
* [WebSocket](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#messaging.websockets)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#howto.batch)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#io.email)
* [Quartz Scheduler](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#io.quartz)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)
* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

