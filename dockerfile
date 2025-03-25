# Use the Eclipse temurin alpine official image
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:21-jdk-alpine

# Create and change to the app directory.
WORKDIR /app

# Copy local code to the container image.
COPY . ./

# Build the app.
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

# Run the app by dynamically finding the JAR file in the target directory
CMD ["sh", "-c", "java -jar target/*.jar"]