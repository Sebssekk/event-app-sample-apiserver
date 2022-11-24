FROM maven:3-amazoncorretto-19 AS build

WORKDIR /app

COPY . .

RUN mvn clean package



FROM amazoncorretto:19-alpine-jdk

WORKDIR /app

COPY --from=build /app/target/apiServer-0.0.1-SNAPSHOT.jar ./app.jar

CMD java -jar app.jar