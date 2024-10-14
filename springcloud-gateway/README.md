### There is a simple CRUD example using JPA: springcloud-gateway
URLs:
- http://localhost:10000/v3/api-docs
- http://localhost:10000/swagger-ui.html
- http://localhost:10000/api/books/
- http://localhost:10000/jpademo/api/books/ -will route to-> http://localhost:10000/api/books/

H2 DataBase:
- http://localhost:10000/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:1st;MODE=MYSQL
    User Name:    sa
</pre>

http://localhost:30000/swagger-ui.html

kubectl -n nsapp logs -f $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep springcloud-gateway | head -1)