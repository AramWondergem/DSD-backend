FROM amazoncorretto:21-alpine

WORKDIR /target

COPY target/apartment-0.0.1-SNAPSHOT.jar /target/apartment-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java" , "-jar", "/target/apartment-0.0.1-SNAPSHOT.jar"]