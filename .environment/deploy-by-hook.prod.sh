#!/bin/sh
docker stop api-prod
docker stop db-postgres-prod
docker image rm -f api-prod db-postgres-prod
docker system prune -f

docker network rm producao
docker network create producao

docker commit api-homolog api-prod:latest
docker commit db-postgres-homolog db-postgres-prod:latest

docker run \
       --name db-postgres-prod \
       --network producao \
       -e POSTGRES_USER=postgres \
       -e POSTGRES_PASSWORD=postgres \
       -v postgres-data:/var/lib/postgresql/data \
       -p 5432:5432 \
       -d postgres:latest

#docker cp /home/univates/app-docker/ini-database.sh db-postgres-prod:/tmp/
#docker exec db-postgres-prod bash /tmp/init-database.sh

docker run -p 9002:9000 -d --env-file .env-file --name api-prod --network producao api-prod:latest java ${ADDITIONAL_OPTS} -jar /app-api/personal-manager-api.jar --spring.profiles.active=prod

