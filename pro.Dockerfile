FROM maven:3-amazoncorretto-19 AS build

WORKDIR /app

COPY . .

RUN mvn clean package



FROM amazoncorretto:19-alpine-jdk

# For health check 
RUN apk --no-cache add curl 

WORKDIR /app

COPY --from=build /app/target/apiServer-0.0.1.jar ./app.jar

CMD java -jar app.jar