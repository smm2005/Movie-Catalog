# BUILD

FROM maven:latest AS build

WORKDIR /app/server

COPY ./pom.xml /app/server/pom.xml
COPY ./src /app/server/src
COPY ./9000plus.csv /app/server/9000plus.csv
COPY ./certsgen.sh /app/server/certsgen.sh

RUN chmod +x ./certsgen.sh
RUN ./certsgen.sh

RUN mvn clean install -DskipTests

CMD ["./mvnw", "spring-boot:run"]

# RUN
FROM eclipse-temurin:21

WORKDIR /app/server/run
EXPOSE 8080

COPY --from=build /app/server/target/*.jar /app/server/run/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]