### There is a simple CRUD example using JPA: resumble-upload

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
- http://localhost:18264/api-docs
- http://localhost:18264/swagger-ui/index.html
- http://localhost:18264/api/books/
- http://localhost:18264/actuator
- 
H2 DataBase:
- http://localhost:18264/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:resumble-upload;MODE=MYSQL
    User Name:    sa
</pre>

Shell curl scripts:
```shell
#! /bin/bash
set -eu

curl -ikLv --request GET \
  --url http://localhost:18264/api-docs \
  --header 'User-Agent: insomnia/10.1.0'

curl -ikLv --request GET \
  --url http://localhost:18264/api/books/ \
  --header 'User-Agent: insomnia/10.1.0'

curl -ikLv --request GET \
  --url http://localhost:18264/actuator \
  --header 'User-Agent: insomnia/10.1.0'

curl -ikLv -X 'POST' \
  'http://localhost:18264/api/books/' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Books20241017",
  "author": "ps",
  "price": 170,
  "publishDate": "2024-10-17"
}'


echo ' '
read -p "Press any key..."
    
```

---
#### Run with kubernetes:
- Make sure you mvn rebuild the latest jar!!!
- Please set NEXUS_USER & NEXUS_PASS if you'd like to connect to NEXUS.
- docker push xxx.com/path/repo/resumble-upload
- If no NEXUS repo, comment out "docker push xxx.com/path/repo/resumble-upload" in dockerBuildPushAndGenDeployYaml.sh
- If no NEXUS repo, fix the: "xxx.com/path/repo/resumble-upload:THE_APP_VERSION" in deploy-template.yaml
- ./buildYamlAndDeploy2k8sCluster.sh to deploy jar to kubernetes.
- Command to view deployment in k8s: kubectl -n nsapp get all
- Command to remove the deployment: kubectl delete namespace nsapp
- OR: kubectl delete -f deploy.yaml
- Check event: kubectl -n nsapp get event
- Check log: kubectl -n nsapp logs -f ngx-5d7556ffd-d7jwt -c 1st
- OR: kubectl -n nsapp logs -f $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep resumble-upload | head -1)
- OR: kubectl -n nsapp -it exec $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep resumble-upload | head -1) -- bash -c "ls"
- Check container: kubectl -n nsapp -it exec podxxx -- bash 
- OR: kubectl -n nsapp -it exec ngx-65f68876c8-ksc74  -c 1st -- /bin/sh
--- 
- Visit Application via:
  URLs:
- http://localhost:38264/api-docs
- http://localhost:38264/swagger-ui/index.html
- http://localhost:38264/api/books/
- http://localhost:38264/actuator
-
H2 DataBase:
- http://localhost:38264/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:resumble-upload;MODE=MYSQL
    User Name:    sa
</pre>

---