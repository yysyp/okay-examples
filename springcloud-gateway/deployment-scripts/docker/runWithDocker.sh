#! /bin/bash
set -eu

. ./0setenv.sh

cd ../../

#echo "Make sure you mvn rebuild the latest jar!!!"
mvn clean package

docker build -t springcloud-gateway:${appversion} -f Dockerfile .
#echo 'password123' | docker login --username user123 --password-stdin https://xxx.com:12345
#docker tag springcloud-gateway:${appversion} xxx.com/path/repo/springcloud-gateway:${appversion}
#docker push xxx.com/path/repo/springcloud-gateway:${appversion}

docker run --name springcloud-gateway --rm -itd --add-host=host.docker.internal:host-gateway -p 10000:10000 springcloud-gateway:${appversion}
