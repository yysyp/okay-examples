#! /bin/bash
set -eu

. ./0setenv.sh

cd ../../

#echo "Make sure you mvn rebuild the latest jar!!!"
mvn clean package

docker build -t user-mngmnt:${appversion} -f Dockerfile .
#echo 'password123' | docker login --username user123 --password-stdin https://xxx.com:12345
#docker tag user-mngmnt:${appversion} xxx.com/path/repo/user-mngmnt:${appversion}
#docker push xxx.com/path/repo/user-mngmnt:${appversion}

docker run --name user-mngmnt --rm -itd --add-host=host.docker.internal:host-gateway -p 17450:17450 user-mngmnt:${appversion}
