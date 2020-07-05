# Document Analyzer
Spring Boot Application to analyze text documents

## What do you need
- JDK 11
- Maven 3+ 

## Technologies 
- Spring Boot
- Spring Web
- Spring Validation
- Spring Data JPA / Hibernate
- H2 Database
- Lombok

## Data sample
Users created via SQL script: src\main\resources\data.sql

Data:
- 3 users: user1@gmail.com, user2@gmail.com, user3@gmail.com
- 3 documents: sample1.txt, sample2.txt, sample3.txt
- 2 teams: team1, team2

User1 uploaded {sample1.txt, sample2.txt}. User2 uploaded {sample3.txt}

Team1 has {user1, user3}. Team2 has {user1, user2}

More details, please check "data.sql" file.

## Endpoints

The Postman collection with all the available endpoints can be found on the application root:
Document Analyzer.postman_collection.json




### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-validation)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

