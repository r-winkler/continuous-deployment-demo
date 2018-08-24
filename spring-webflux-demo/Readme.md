### start postgres 
docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres

### start pgadmin
docker run -p 80:80 -e "PGADMIN_DEFAULT_EMAIL=user@domain.com" -e "PGADMIN_DEFAULT_PASSWORD=SuperSecret" -d dpage/pgadmin4

- user: postgres
- password: mysecretpassword
- host: see ip of docker container with docker inspect
- port 5432

### excecute flyway commands
docker run --rm -v /home/rene/IdeaProjects/spring-webflux-demo/src/main/resources/db/migration:/flyway/sql -v /home/rene/IdeaProjects/spring-webflux-demo/src/main/resources:/flyway/conf boxfuse/flyway info

