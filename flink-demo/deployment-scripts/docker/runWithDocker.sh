#! /bin/bash
set -eu

. ./0setenv.sh

cd ../../

#echo "Make sure you mvn rebuild the latest jar!!!"
mvn clean package

docker build -t flink-demo:${appversion} -f Dockerfile .
#echo 'password123' | docker login --username user123 --password-stdin https://xxx.com:12345
#docker tag flink-demo:${appversion} xxx.com/path/repo/flink-demo:${appversion}
#docker push xxx.com/path/repo/flink-demo:${appversion}

docker run --name flink-demo --rm -itd --add-host=host.docker.internal:host-gateway -p 172:172 flink-demo:${appversion}
