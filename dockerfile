FROM amazoncorretto:21-alpine

WORKDIR /app

COPY target/apartment.jar /app/apartment.jar

expose 8080

CMD ["java" , "-jar", "/app/apartment.jar"]