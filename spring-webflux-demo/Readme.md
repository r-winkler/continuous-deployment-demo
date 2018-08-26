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
# .env file is only supported for docker-compsoe: see https://github.com/moby/moby/issues/29133
# this is a workaround
env $(cat .env | grep ^[A-Z] | xargs) docker -H 127.0.0.1:2375 stack deploy --compose-file docker-compose.yml demo



### Connect to Vagrant via Putty
1) go to vagrant directory and input 'vagrant ssh-config' (you will see all connection information like hostname, user and port)
2) convert private_key (see path in step 1) in OpenSSH format to putty-format: puttygen private_key -O private -o key.ppk
3) Open putty and specify the private key (key.ppk) to establish the connection


### Connect to Vagrant via ssh
1) Generate pem File (see path of private_key via 'vagrant ssh-config'):
    openssl rsa -in private_key -outform pem > key.pem
    chmod 600 key.pem
2) sudo ssh -oStrictHostKeyChecking=no -oUserKnownHostsFile=/dev/null -i /home/rene/IdeaProjects/continuous-deployment-demo/vagrant/multi-enviornment/.vagrant/machines/node1/virtualbox/key.pem vagrant@192.168.33.10


### Connect with Docker Client on host via SSH tunnel to Docker Daemon in Vagrant VM
1) Generate pem File (see path of private_key via 'vagrant ssh-config'):
    openssl rsa -in private_key -outform pem > key.pem
    chmod 600 key.pem
2) Add User to docker group
   sudo usermod -aG docker $USER
   sudo su vagrant
3) Open a SSL Tunnel on the Host (see https://sysadmins.co.za/forwarding-the-docker-socket-via-a-ssh-tunnel-to-execute-docker-commands-locally/) 
    screen -S node1
    sudo ssh -oStrictHostKeyChecking=no -oUserKnownHostsFile=/dev/null -i /home/rene/IdeaProjects/continuous-deployment-demo/vagrant/multi-enviornment/.vagrant/machines/node1/virtualbox/key.pem -NL 127.0.0.1:2375:/var/run/docker.sock vagrant@192.168.33.10
4) docker -H 127.0.0.1:2375 info


