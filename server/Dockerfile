# BUILD

FROM maven:latest AS build

WORKDIR /app/server

COPY ./pom.xml /app/server/pom.xml
COPY ./.mvn /app/server/.mvn
COPY ./src /app/server/src
COPY ./certsgen.sh /app/server/certsgen.sh

RUN chmod +x /app/server/certsgen.sh
RUN /app/server/certsgen.sh

RUN mvn clean install -DskipTests

# RUN

FROM eclipse-temurin:21

COPY --from=build /app/server/target/*.jar app.jar

EXPOSE 3000

CMD [ "java", "-jar", "app.jar" ]