# Fuel consumption management application

### Description
The application reports purchased fuel volumes and retrieve the already inserted information by other contributors. <br>
The application has only RESTful API with JSON payloads in response. <br>
Fuel consumption registration accepts: <br>
-	fuel type(Ex. 95, 98 or D) 
-	price per litter in EUR (Ex. 10.10
-	volume in litters (Ex. 12.5)
-	date (Ex. 01.21.2018)
-	driver ID (Ex. 12345) <br>

The application can register one single record or bulk consumption
(multiple records in one file, for example multipart/form-data).


### Used stack:
<b>Backend:</b> Jdk 1.8, Spring Boot, Spring Data, Hibernate<br>
<b>Database:</b> H2<br>
<b>Tests:</b> JUnit5, Mockito<br>

##### Build program
```bash
mvn clean install -DskipTests
```
Move to the folder, where the file `fuel-consumption.jar` was created. `cd target`

##### Run program
```bash
java -jar fuel-consumption.jar
```
The application contains initial data. They can be deleted in file `resources/data.sql`


##### RESTful API

`POST http://localhost:8080/consumption`   <br>
Register one single record

`POST http://localhost:8080/consumption/bulk` <br>
Register bulk consumption (multiple records in one file)

`GET http://localhost:8080/consumption/money`  <br>
Total spent amount of money grouped by month for all drivers  

`GET http://localhost:8080/consumption/money/driver/{id}`   <br>
Total spent amount of money grouped by month for the specific driver
   
`GET http://localhost:8080/consumption/month/{month}`   <br>
List fuel consumption records for specified month for all drivers
(records contain: fuel type, volume, date, price, total price, driver ID)
   
`GET http://localhost:8080/consumption/month/{month}/driver/{id}`   <br>
List fuel consumption records for specified month for the specific driver
(records contain: fuel type, volume, date, price, total price, driver ID)
 
`GET http://localhost:8080/consumption/statistic`  <br>
Statistics for each month, list fuel consumption records grouped by fuel type for all drivers
(each row contains: fuel type, volume, average price, total price)

`GET http://localhost:8080/consumption/statistic/driver/{id}`  <br>
Statistics for each month, list fuel consumption records grouped by fuel type for the specific driver
(each row contains: fuel type, volume, average price, total price)

