# SecureAngular1-SecureSpringBoot
#

Demonstrates security and authorization of an angular 1 front end communicating using REST with a secure back end with spring boot , spring spring mvc and spring security 4. 

Especially demonstrates best practices for the implementation of protections against CSRF and CORS in back end and front end *sides.*

Including Spring security *tests* 

bakendSecure : Spring boot backend
---
1. Install java 8
2. cd bakendSecure
3. mvn install
4. mvn spring-boot:run  ---> Tomcat started on port(s): 8081

angular_api_client : Angular 1 frontend 
---

1. Install node, npm, bower
2. cd angular_api_client
3. npm install
4. grunt serve  --> Started connect web server on http://localhost:9000
