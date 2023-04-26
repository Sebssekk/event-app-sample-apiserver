FROM maven:3-amazoncorretto-19

WORKDIR /app

COPY . .

RUN mvn clean package

CMD java -jar target/apiServer-0.0.1.jar