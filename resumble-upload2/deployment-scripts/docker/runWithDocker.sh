#! /bin/bash
set -eu

. ./0setenv.sh

cd ../../

#echo "Make sure you mvn rebuild the latest jar!!!"
mvn clean package

docker build -t resumble-upload2:${appversion} -f Dockerfile .
#echo 'password123' | docker login --username user123 --password-stdin https://xxx.com:12345
#docker tag resumble-upload2:${appversion} xxx.com/path/repo/resumble-upload2:${appversion}
#docker push xxx.com/path/repo/resumble-upload2:${appversion}

docker run --name resumble-upload2 --rm -itd --add-host=host.docker.internal:host-gateway -p 17294:17294 resumble-upload2:${appversion}
