## How to get started

### Prerequisites
- Docker Desktop https://docs.docker.com/desktop/
- java version: 21 (https://www.geeksforgeeks.org/download-install-java-windows-linux-macos/). Make sure you also install the `JAVA_HOME` variable discussed in the link. For more info about `JAVA_HOME` use https://www.baeldung.com/java-home-on-windows-mac-os-x-linux
- ide. For backend development, IntelliJ is recommend. It has a free community version. https://www.jetbrains.com/idea/download/?section=windows No ide is needed when you are only running the backend to work on the frontend. The instructions below are all in the terminal.

### Running application
- Go to dir with the backend in the terminal. If you opened the project in your IDE, the terminal is already pointing to the right folder. 
- Run in terminal `docker compose up -d` to start up docker container with postgres database
- Set the environment variable `JWT_SECRET_ENV`. It should be a string of at least 32 characters. An example could be: `THisIsaveryLongJWTsecretThatshouldBeALittleBitLongerITHinkItisLongEnough`. If you want to use the JWT token from a previous session, you must ensure that the secret is the same secret. I set a temperary variable in my terminal at the begiining of the day once like this: 
  - Powershell: run `$env:JWT_SECRET_ENV="{YOUR_SECRET}"`. You can check with `echo $env:JWT_SECRET_ENV` if you set the variable
  - Mac/Linux: run `export JWT_SECRET_ENV="{YOUR_SECRET}""`. You can check with `echo $JWT_SECRET_ENV` if you set the variable
- Run the following command in the terminal to start up the backend
  - Powershell: `./mvnw.cmd spring-boot:run`
  - Mac/Linux `./mvnw spring-boot:run`. It could be that you have to run `chmod +x mvnw` before you can run `spring-boot:run`
- To use lease services, need to set api key, you need to either ask ruben or can create one of your own. If you create your own, you need to set the callback url to receive callbacks from dropbox:
  - Bash `export DROPBOX_API_KEY="{YOUR_SECRET}"` 
- To run with Ngrok, need to set spring profile to dev: 
  - Bash: `export SPRING_PROFILES_ACTIVE=dev`
  - Powershell: `$env:SPRING_PROFILES_ACTIVE="dev"`
  - get a ngrok token : https://dashboard.ngrok.com/get-started/your-authtoken, or ask ruben
  - set ngrok token : `export NGROK_AUTHTOKEN="{your-ngrok-authtoken}"` via bash
  - start backend `mvn spring-boot:run`

### Shutting down application
- In the terminal use `ctrl+c` to shut down the application
- Run `docker compose down --volumes` to shut down the database

## Swagger

You can go to http://localhost:8080/swagger-ui/index.html to view the open api definition provided by swagger while running the application

### MapStruct

MapStruct is a package that generates the boilerplate code for mappers. If you name the variables in the entities and DTOs the same, it makes you live really easy. See for quick guide: https://www.baeldung.com/mapstruct


## to use h2 db to persist while we setup postgres
I added a h2 db to get started persisting and building out your services. Meanwhile we decide where to host our postgres db and backend.
find more info here: https://www.h2database.com/html/main.html , https://howtodoinjava.com/spring-boot/h2-database-example/
after running the spring boot app in
- Bash: `export SPRING_PROFILES_ACTIVE=dev`
- start backend `mvn spring-boot:run`

``http://localhost:8080/h2-console`` (assuming hosted on port 8080)
you should see: 
![img.png](img.png)

and use:
the saved setting: Generic H2 (Embedded)

setting name: Generic H2(Embedded)

driver class: org.h2.Driver

jdbc url: jdbc:h2:file:./super_backend/data/mydb

username: sa

password:

password is a blank space*  

screen should be like this

once you logged in this should be what you see. 
![img_1.png](img_1.png)


## Entity diagram with PlantUML plugin

You need to install the plugin in IntelliJ to create the diagram.y

 - plugin: https://plugins.jetbrains.com/plugin/7017-plantuml-integration
 - documentation: https://plantuml.com/class-diagram
