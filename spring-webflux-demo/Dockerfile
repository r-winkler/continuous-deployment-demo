# Start with a base image containing Java runtime
FROM openjdk:10

# Add Maintainer Info
LABEL maintainer="René Winkler"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The database host (resolves to ip address)
ENV DATABASE_HOST=placeholder_since_env_must_have_a_default_value

# The project version
ARG PROJECT_NAME
ARG PROJECT_VERSION

# The application's jar file
ARG JAR_FILE=target/${PROJECT_NAME}-${PROJECT_VERSION}.jar

# Add the application's jar to the container
ADD ${JAR_FILE} ${PROJECT_NAME}.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/demo.jar", "--spring.datasource.url", "jdbc:postgresql://${DATABASE_HOST}:5432/postgres"]