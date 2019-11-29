## Campaigns REST API example
This is a simple example how you can use spring boot + mongodb + docker to create a rest api. Any suggestions, new features or fixes are welcome!

- [x] Spring Boot
- [x] Spring Data
- [x] MongoDB 
- [x] Log4j2 (instead of logback)
- [x] Docker
- [x] Swagger 2 (api documentation)
- [x] CORS filter
- [ ] Tests
- [ ] CI/CD tool

### System requirements
- JDK 11
- Maven
- Docker

### API documentation 
This example uses Swagger 2. Access to http://localhost:8080/swagger-ui.html to check the documentation.

### Build and run API
All the configurations (including environment variables used by the application) are in the file [docker-compose.yml](docker-compose.yml). Using the docker-compose, you need first to build the docker image(s) (`docker-compose build`) to include any code changes and then run `docker-compose up` to startup the services (use `-d` to run in detached mode). The docker image for the rest api application service is defined by the file [Dockerfile](Dockerfile) to build and execute the application using multi stages.

If you prefer to not use docker, you can build manually the code in order to generate the executable file (`mvn clean package`) and then run it using the maven spring boot plugin (`mvn spring-boot:run`) or directly by the java command  `java -jar <jar file location>`. Do not forget to set the required environment variables for the [application.properties](/src/main/resources/application.properties) and make sure that you're running a mongoDB server instance in your environment.

The rest api application will be available at http://localhost:8080.

Enjoy!