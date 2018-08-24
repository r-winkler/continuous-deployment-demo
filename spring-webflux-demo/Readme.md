### start postgres 
docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres

### start pgadmin
docker run -p 80:80 -e "PGADMIN_DEFAULT_EMAIL=user@domain.com" -e "PGADMIN_DEFAULT_PASSWORD=SuperSecret" -d dpage/pgadmin4

- user: postgres
- password: mysecretpassword
- host: see ip of docker container with docker inspect
- port 5432

### build app
docker build . --build-arg PROJECT_NAME=demo --build-arg  PROJECT_VERSION=0.0.1-SNAPSHOT -t spring-webflux-demo

### run app
docker run -itd -e DATABASE_HOST=172.17.0.2 -p 8080:8080 spring-webflux-demo

### excecute flyway commands
docker run --rm -v /home/rene/IdeaProjects/spring-webflux-demo/src/main/resources/db/migration:/flyway/sql -v /home/rene/IdeaProjects/spring-webflux-demo/src/main/resources:/flyway/conf boxfuse/flyway info

### run by docker-compose
docker-compose -p first up -d --build --force-recreate
docker-compose -p second up -d --build --force-recreate
docker-compose -p third up -d --build --force-recreate

### stop by docker-compose
docker-compose -p first down
docker-compose -p second down
docker-compose -p third down

