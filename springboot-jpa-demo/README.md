### There is a simple CRUD example using JPA: springboot-jpa-demo

---
#### Run with docker:
- Make sure you mvn rebuild the latest jar!!!
- Please set NEXUS_USER & NEXUS_PASS if you'd like to connect to NEXUS.
- Go to folder: deployment-scripts/docker/ and open "Git bash here".
- ./runWithDocker.sh to deploy jar to docker.
- Input Nexus password if any
- Command to stop instance: docker stop xxxxx
---
- Visit Application via:
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
- Make sure you mvn rebuild the latest jar!!!
- Please set NEXUS_USER & NEXUS_PASS if you'd like to connect to NEXUS.
- docker push xxx.com/path/repo/springboot-jpa-demo
- If no NEXUS repo, comment out "docker push xxx.com/path/repo/springboot-jpa-demo" in dockerBuildPushAndGenDeployYaml.sh
- If no NEXUS repo, fix the: "xxx.com/path/repo/springboot-jpa-demo:THE_APP_VERSION" in deploy-template.yaml
- ./buildYamlAndDeploy2k8sCluster.sh to deploy jar to kubernetes.
- Command to view deployment in k8s: kubectl -n nsapp get all
- Command to remove the deployment: kubectl delete namespace nsapp
- OR: kubectl delete -f deploy.yaml
- Check event: kubectl -n nsapp get event
- Check log: kubectl -n nsapp logs -f ngx-5d7556ffd-d7jwt -c 1st
- OR: kubectl -n nsapp logs -f $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep springboot-jpa-demo | head -1)
- OR: kubectl -n nsapp -it exec $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep springboot-jpa-demo | head -1) -- bash
- Check container: kubectl -n nsapp -it exec podxxx -- bash 
- OR: kubectl -n nsapp -it exec ngx-65f68876c8-ksc74  -c 1st -- /bin/sh
--- 
- Visit Application via:
  URLs:
- http://localhost:30001/api-docs
- http://localhost:30001/swagger-ui/index.html
- http://localhost:30001/api/books/
- http://localhost:30001/actuator
-
H2 DataBase:
- http://localhost:30001/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:springboot-jpa-demo;MODE=MYSQL
    User Name:    sa
</pre>

---