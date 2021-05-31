[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mohdjeeshan)

# Update Password using Spring Boot

## Table of Contents

- [Authors](#authors)
- [Pre-requisites](#pre-requisites)
- [Framework](#framework)
- [Clone Project](#clone-project)
- [Build Appplication](#build-project)
- [Running Tests](#running-tests)
- [Postman Tests](#postman-tests)
- [Dockerized Application](#dockerized-application)

## Authors
* [Mohd Jeeshan](https://github.com/jeeshan12)

## Pre-requisites
Please install below tools to use this project

* [Java](https://www.java.com/en/download/)
* [Maven](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/)
* [IntelliJ](https://www.jetbrains.com/idea/download/) **(optional)**

## Framework
Below libraries are used to automate the web browsers
* [Spring Boot](https://testng.org/doc/) - Spring Boot is an open source Java-based framework used to create a micro Service.
* [Mockito](https://site.mockito.org/) - to Mock the API.
* [Rest Assured](https://github.com/bonigarcia/webdrivermanager) - to automate REST API with some real data.
* [Faker](https://github.com/DiUS/java-faker) - a library to generate random data
* [h2 database](https://www.h2database.com/html/main.html) - in memory database to store some records for old passwords


## Clone the project
Clone the project by running below command in terminal
```
git clone https://github.com/jeeshan12/springboot-updatepassword.git
```

## Build Appplication
### Install dependencies
Open the terminal in the root project and run the below task
```
mvn clean install -DskipTests=true
```
### Application can be build in 2 ways
* Using **Maven**
```
From root of your project open terminal and run the command mvn spring-boot:run
```
* Using **jar** file
```
For running application using jar file , we need to perform 2 steps
a) From root of your project open terminal and run the command mvn clean package -DskipTests
b) Go to target folder and open your terminal and run command java -jar changepasswordservice.jar
```

### h2 database
Once application is up and running 4 users will inserted into database automagically. You can access the h2- console in your browser window by hitting url [h2-console](http://localhost:8080/console/h2-console). UI will look something like this.
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/h2-db-console.png)
After login you can run Select command to see all the records
```
SELECT * FROM USER
```
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/DBRecords.png)
## Running Tests
There are 2 type of tests are written. **Controller Tests( Unit Tests)** which uses the mocking to test the rest endpoints and ***Rest Assured Tests*** for E2E API Tests.
Once application is up and running, please run below command to run your tests.
```
mvn test
```
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/mocktests.png)
## Running Through IDE
You can also execute the tests through IntelliJ, VS Code or Eclpise.
There are 2 classes present in `src/test/java`
* [PasswordValidatorApplicationTests.java](https://github.com/jeeshan12/springboot-updatepassword/blob/main/src/test/java/com/password/PasswordValidatorApplicationTests.java)  : Rest Assured Tests for API
* [UserControllerTest](https://github.com/jeeshan12/springboot-updatepassword/tree/main/src/test/java/com/password/controller/UserControllerTest) : Mock API Tests

```
PS: You can run mock tets even without running the application as data is mocked here.
```

## Postman Tests
Base URI for change password service is `http://localhost:8080/user`
For updating the password the **end point** exposed is `/changepassword/{{id}}` where id can be between `1-4`. Again this id depends on the number on records inserted into database. As of now only 4 records are there but if you wish to increase the number of default records you can do it easily by inserting data to  [data.sql](https://github.com/jeeshan12/springboot-updatepassword/blob/main/src/main/resources/data.sql) file.
To test the service in postman we need to do a `POST ` to the end points. A screenshot for reference is attached.
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/postman_post.png)
You can import the collection in `Postman` from [here](https://github.com/jeeshan12/springboot-updatepassword/tree/main/postmancollection).

## Dockerized Application

To Dockerize Spring Boot application
* Go to root directory of the framework (make sure `Dockerfile is in root directory`) and build the image by running command
```
docker build -t jeeshan/passwordvalidator . 
You will get something similar to this in console once image is built
```
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/dockerbuild.png)
* Now Start the `Spring Boot` by running below command
```
 docker-compose up 
 You will get something similar in console. 
```
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/dockercomposeup.png)
* You can test the service in Postman by hitting endpoints(covered in last section) or by running Automated [Rest Assured Tests](https://github.com/jeeshan12/springboot-updatepassword/blob/main/src/test/java/com/password/PasswordValidatorApplicationTests.java) .
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/restassuredtests.png)

### Tear Down docker compose

Run the below command to bring the `services` down
```
docker-compose down
```
![](https://github.com/jeeshan12/springboot-updatepassword/blob/main/screenshots/dopckercomposedown.png)
