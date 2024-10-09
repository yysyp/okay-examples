### There is a simple CRUD example using JPA: springboot-jpa-demo

---
#### Run with docker:
- Make sure you mvn rebuild the latest jar!!!
- Please set NEXUS_USER & NEXUS_PASS if you'd like to connect to NEXUS.
- Go to folder: deployment-scripts/docker/ and open "Git bash here".
- ./runWithDocker.sh
- Input Nexus password if any
- Command to stop instance: docker stop xxxxx

---
#### Visit Application:
URLs:
- http://localhost:10001/api-docs
- http://localhost:10001/swagger-ui/index.html
- http://localhost:10001/api/books/
- http://localhost:10001/actuator
- 
H2 DataBase:
- http://localhost:10001/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:springboot-jpa-demo;MODE=MYSQL
    User Name:    sa
</pre>

---
#### Run with kubernetes:
