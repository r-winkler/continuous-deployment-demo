# Start with a base image containing Java runtime
FROM openjdk:10

# Add Maintainer Info
LABEL maintainer="René Winkler"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The database host (resolves to ip address)
ENV DATABASE_HOST=placeholder_since_env_must_have_a_default_value

ARG PROJECT_NAME_BACKEND
ARG PROJECT_VERSION
ARG JAR_FILE=target/${PROJECT_NAME_BACKEND}-${PROJECT_VERSION}.jar

# Add the application's jar to the container
ADD ${JAR_FILE} demo.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/demo.jar", "--spring.datasource.url", "jdbc:postgresql://${DATABASE_HOST}:5432/postgres", "--spring.datasource.username", "${POSTGRES_USER}", "--spring.datasource.password", "${POSTGRES_PASSWORD}"]



# docker build . --build-arg PROJECT_NAME_BACKEND=spring-webflux-demo --build-arg  PROJECT_VERSION=0.0.1-SNAPSHOT -t spring-webflux-demo
# docker run -itd -e DATABASE_HOST=172.17.0.2 -p 8080:8080 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=mysecretpassword spring-webflux-demo