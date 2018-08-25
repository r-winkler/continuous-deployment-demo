### start postgres 
docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres

### start pgadmin
docker run -p 80:80 -e "PGADMIN_DEFAULT_EMAIL=user@domain.com" -e "PGADMIN_DEFAULT_PASSWORD=SuperSecret" -d dpage/pgadmin4

- user: postgres
- password: mysecretpassword
- host: see ip of docker container with docker inspect
- port 5432

### excecute flyway commands
docker run --rm -v /home/rene/IdeaProjects/continuous-deployment-demo/spring-webflux-demo/src/main/resources/db/migration:/flyway/sql -v /home/rene/IdeaProjects/spring-webflux-demo/src/main/resources:/flyway/conf boxfuse/flyway info

### manually deploy to swarm
vagrant up
vagrant ssh node1
docker swarm init --advertise-addr 192.168.33.10
'add node2 to swarm'


### Connect to Vagrant via Putty
1) go to vagrant directory and input 'vagrant ssh-config' (you will see all conenction information like hostname, user and port)
2) convert private_key in OpenSSH format (see location in step 1) to putty-format: puttygen private_key -O private -o key.ppk
3) Open putty and specify the private key to establish the connection

