docker run --detach --env MYSQL_ROOT_PASSWORD=root --env MYSQL_DATABASE=mydb --env MYSQL_PASSWORD=root --env MYSQL_USER=admin --name localhost --publish 3306:3306 mysql:8.0
docker run --name postgres-tutorial -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres
