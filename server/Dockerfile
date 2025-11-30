# BUILD

FROM maven:latest AS build

WORKDIR /app/server

COPY ./pom.xml /app/server/pom.xml
COPY ./src /app/server/src
COPY ./certsgen.sh /app/server/certsgen.sh

EXPOSE 80

RUN chmod +x ./certsgen.sh
RUN ./certsgen.sh

RUN mvn clean install -DskipTests

CMD ["./mvnw", "spring-boot:run"]

# RUN
FROM eclipse-temurin:21

COPY --from=build /app/server/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]