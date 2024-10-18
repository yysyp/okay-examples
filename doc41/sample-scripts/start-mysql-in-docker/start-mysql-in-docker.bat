docker build -t mysql57x -f docker-mysql/Dockerfile docker-mysql
docker run --name mysql57x -e MYSQL_ROOT_PASSWORD=root -d --add-host=host.docker.internal:host-gateway -p 3306:3306 mysql57x
