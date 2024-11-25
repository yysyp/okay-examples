#! /bin/bash
set -eu

. ./0setenv.sh

cd ../../

#echo "Make sure you mvn rebuild the latest jar!!!"
mvn clean package

docker build -t resumble-upload:${appversion} -f Dockerfile .
#echo 'password123' | docker login --username user123 --password-stdin https://xxx.com:12345
#docker tag resumble-upload:${appversion} xxx.com/path/repo/resumble-upload:${appversion}
#docker push xxx.com/path/repo/resumble-upload:${appversion}

docker run --name resumble-upload --rm -itd --add-host=host.docker.internal:host-gateway -p 18264:18264 resumble-upload:${appversion}
