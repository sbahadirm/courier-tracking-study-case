# Courier Tracking System

### About The Project
Courier Tracking System is an online logging application that allows logging of couriers' locations. It also reports delivery times.
When 100 meters approach to the stores, it is considered to have entered the store. 
Re-entry will not be counted if still in a similar location in less than 1 minute.

### Summary

In this project;

- Each module has three letter aliases.  Every classes or interfaces that related with it start with these alliases.
- Dependencies are managed by Maven.
- Every request is validated with Spring Validation
- Documented with Swagger.
- Endpoints use DTOs instead of entities. Convert operations are done with mapstruct.
- Tests were written with Junit&Mockito.
- Lombok was used. 
- The structure of the all responses are same. It can be understood from the isSuccess field that the requests are successful or unsuccessful.

Module  | Allias
------------- | -------------
Courier Tracking System  | Cts
Delivery  | Dlv
Store  | Str 
Generic  | Gen 


### Technologies
- Java 17
- PostgreSQL
- Spring Boot 
- Maven
- Junit, Mockito
- Swagger
- Lombok
- Mapstruct

### Prerequisites
- Java 17 or never
- Docker Desktop

# Installation
1. Clone the repo

```sh
https://github.com/sbahadirm/courier-tracking-study-case.git
```

2. Run Docker-Compose file 
```powershell
 > mvn clean install
```

3. Run Docker-Compose file 
```powershell
 > docker-compose up
```
   
