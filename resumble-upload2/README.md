### There is a simple CRUD example using JPA: resumble-upload2

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
- http://localhost:17294/api-docs
- http://localhost:17294/swagger-ui/index.html
- http://localhost:17294/api/books/
- http://localhost:17294/actuator
- 
H2 DataBase:
- http://localhost:17294/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:resumble-upload2;MODE=MYSQL
    User Name:    sa
</pre>

Shell curl scripts:
```shell
#! /bin/bash
set -eu

curl -ikLv --request GET \
  --url http://localhost:17294/api-docs \
  --header 'User-Agent: insomnia/10.1.0'

curl -ikLv --request GET \
  --url http://localhost:17294/api/books/ \
  --header 'User-Agent: insomnia/10.1.0'

curl -ikLv --request GET \
  --url http://localhost:17294/actuator \
  --header 'User-Agent: insomnia/10.1.0'

curl -ikLv -X 'POST' \
  'http://localhost:17294/api/books/' \
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
- docker push xxx.com/path/repo/resumble-upload2
- If no NEXUS repo, comment out "docker push xxx.com/path/repo/resumble-upload2" in dockerBuildPushAndGenDeployYaml.sh
- If no NEXUS repo, fix the: "xxx.com/path/repo/resumble-upload2:THE_APP_VERSION" in deploy-template.yaml
- ./buildYamlAndDeploy2k8sCluster.sh to deploy jar to kubernetes.
- Command to view deployment in k8s: kubectl -n nsapp get all
- Command to remove the deployment: kubectl delete namespace nsapp
- OR: kubectl delete -f deploy.yaml
- Check event: kubectl -n nsapp get event
- Check log: kubectl -n nsapp logs -f ngx-5d7556ffd-d7jwt -c 1st
- OR: kubectl -n nsapp logs -f $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep resumble-upload2 | head -1)
- OR: kubectl -n nsapp -it exec $(kubectl -n nsapp get pods --field-selector status.phase=Running --no-headers -o custom-columns=":metadata.name" | grep resumble-upload2 | head -1) -- bash -c "ls"
- Check container: kubectl -n nsapp -it exec podxxx -- bash 
- OR: kubectl -n nsapp -it exec ngx-65f68876c8-ksc74  -c 1st -- /bin/sh
--- 
- Visit Application via:
  URLs:
- http://localhost:37294/api-docs
- http://localhost:37294/swagger-ui/index.html
- http://localhost:37294/api/books/
- http://localhost:37294/actuator
-
H2 DataBase:
- http://localhost:37294/h2-console
<pre>
    Driver Class: org.h2.Driver
    JDBC URL:     jdbc:h2:mem:resumble-upload2;MODE=MYSQL
    User Name:    sa
</pre>

---
#### Frontend setup, in frontend folder:
- Install nodeJS 18+
- Generate .npmrc file to Home folder by: npm config set always_auth true
- Modify the .npmrc file to add: 
```properties
strict-ssl=false
registry=https://xxx/npm-host/
```
- Generate the registry authToken to the .npmrc file by run: npm login n
- If npx command doesn't exist, run: npm i -g npx
- Run: npx create-react-app my-app
- Run: cd my-app
- Run: npm install
- Run: npm start
- Visit: http://localhost:3000/
- Run: npm run build
- Copy all content in build folder, and paste to springboot project folder: src/main/resources/static
- 
