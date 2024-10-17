#! /bin/bash
set -eu

. ./0setenv.sh
appversion=$(date +"%Y%m%d-%H%M%S")
CURDIR=$(pwd)

cd ../../
#echo "Make sure you mvn rebuild the latest jar!!!"
mvn clean package

docker build -t user-mngmnt:${appversion} -f Dockerfile .
#gcloud auth configure-docker xxx.com
docker tag user-mngmnt:${appversion} xxx.com/path/repo/user-mngmnt:${appversion}

#docker push xxx.com/path/repo/user-mngmnt:${appversion}

cd "${CURDIR}"
cp -f deploy-template.yaml deploy.yaml
sed -i "s/THE_APP_VERSION/${appversion}/g" deploy.yaml

