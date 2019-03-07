# Getting Started

### Run Instructions:

In order to run this project with Maven, we have to install maven and set JAVA_HOME to our JDK folder.

#### Option #1:
   Run with Maven in the root folder of the project to package the project and start up the spring boot. The service will be started up at port 8080 by default.
        `mvn package && java -jar target/backend-challenge-0.0.1-SNAPSHOT.jar`

#### Option #2: 
   Open the project in Intellij and run Java Application class BackendChallengeApplication. The service will be started up at port 8080 by default.

### Verify the Result:
 - `curl localhost:8080/continents`: get all countries and continents
 - `curl localhost:8080/continents/<continent name>`: get list of countries belonging to the continent. For example: `curl localhost:8080/continents/Africa`
 - `curl localhost:8080/country/<country name>`: get country information(name, flag) by country name. For example: `curl localhost:8080/country/Ethiopia`
 - `curl localhost:8080/actuator/metrics`: get all available metrics
 - `curl localhost:8080/actuator/metrics/continent.get.<continent name>`: get the metric counter for specific continent. For example: `curl localhost:8080/actuator/metrics/continent.get.Africa`
 - `curl localhost:8080/actuator/metrics/country.get.<country name>`: get the metric counter for specific country.  For example: `curl localhost:8080/actuator/metrics/country.get.Ethiopia`

### Notes:
 - Please note that if you want to enter countries/continents parameter with empty spaces between words, you have to enter the encode string to represent for the empty space. For example, `curl localhost:8080/country/DR Congo` should be: `curl localhost:8080/country/DR%20Congo`
 - `InitializeData.java` is where we load the continents.txt file into H2 database. I’m using JPA to demonstrate this example, we can change to other Repositories we want depending on which datasource we want to use.
 - `ExceptionsNotFoundAdvice.java` is where we handle NotFoundException by handling exceptions we want and return HTTP_NOT_FOUND status to client
 - `ContinentsControllerTests.java`: we use MockMvn from Spring boot for our Rest API testing purpose.
 - `application.properties`: I enabled logs for debugging SQL statement purpose and also enable all endpoints for accessing metrics
 - I used `MetricRegistry` class and a hashmap of <String, Counter> to count the requests to get specific Country/Continent

### Table Schemas:
 - country:  
   `continent_id: BIGINT`  
   `flag: VARCHAR`  
   `name: VARCHAR`  
   `id: BIGINT`  
 SQL: `insert into country (continent_id, flag, name, id) values (?, ?, ?, ?)`
- continent:    
   `continent: VARCHAR`  
   `id: BIGINT`  
 SQL: `insert into continent (continent, id) values (?, ?)`

### Known Issues:
 The flags emoiji is decoded to plain String after storing to database and getting from Rest service. It may not be able to display well in client side. We need to find a better way to map the flag field to appropriate Entity field.
