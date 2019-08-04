# ***REST ExpoProj***
####  **Description** 
This project created as example of implementation Spring Security using ***JSON Web Token***. It`s provide possibility to perform [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) 
operations on Exposition and Hall objects. 
#### **About security:**
When user make request to the path which requires to be authorized, then security Filter expect **Authorization** token in the request`s header. That token contains information about user rights and other **public** information, like _roles_, _username_.
More about **Jwt** you can read [here](https://jwt.io/introduction/).
Information about spring security works read [here](https://spring.io/guides/topicals/spring-security-architecture#_web_security).  It is very important to understand how to config your security. 
**Pay attention** that we use stateless session. 

## Software

 - JDK 8
 - Apache Maven 3.6.0 
 - MariaDB 10.3.17
 - Intellij idea ultimate 
 - Postman
 >Hope it will work on community version

## Installation
- Download _.zip_ from **GitHub** repository
- Unpack archive
-  Install  [MariaDB](https://mariadb.org)
- Install [JDK 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Install [Apache maven](https://maven.apache.org/download.cgi) _([with system variables](https://maven.apache.org/install.html))_ 
- Create database with name ***SpringSPj*** 
```sql
create database SpringSPj character set utf8;
```  
- Open **pom.xml** file: 
-- In **properties** element specify database configuration: *username*, *password*, *DB port*. For example:
```xml
<properties>  
 ...
 <flyway.user>root</flyway.user>  
 <flyway.password>password</flyway.password> 
 <flyway.url>jdbc:mariadb://localhost:3306/SpringSPj</flyway.url>   
 ...
</properties>
```
- Move to root of the project (the same level as _pom.xml_)
- Install all maven dependencies 
```bash script
mvn clean install
```
> Or your IDE do it for you 
- Run migration of SQL scripts to create the tables and fill some sample data
```bash script
mvn flyway:migrate
```

## Running
- Run project with command
```bash script
mvn spring-boot:run
```
> Or press green button in the IDE
- To stop the program press in console
``` 
Ctrl + C
``` 
> Or press red button in the IDE

### Instructions :
- Program running by address http://localhost:8080/expo-calendar/.
- Using browser you can visit only next links :
--  .../exposition/{id}
-- .../expositions
-- .../hall/{id}
-- .../halls
as they do not need admin rights.
##### I highly recommend  to use [Postman](https://www.getpostman.com)    as tool to interact with RESTful applications, as it allows to use all http methods, add security token to your header etc.
#### **The next steps perform in Postman:**
1. Choose **POST** method with the [login](http://localhost:8080/expo-calendar/login) page.
- In the tab **Body** choose **raw** and **JSON (application/json)** parameters  _(by next use this parameters in every request)_ .
2. Enter the next JSON to get token:
```json
{
	"login":"admin",
	"password":"admin"
}
```
3. In header of response you will get token.
4. Copy it.
5. Open **Authorization** tab and choose **Bearer Token**.
6. Paste token **without** prefix **"Bearer"** in input field .

> Leading this steps you get token which allows you to be authorized.
> **Remember:** This token should be in the header of any request where you provide authorization and require proper rights, else you receive _forbidden (403)_ error.
> In **application.properties** file you can set up the expiration time of every token. After token expire in the next request you receive error with proper description. 

#### Other endpoints in the program (required admin rights):
- .../admin/exposition/{id} _(put, delete)_ 
- .../admin/exposition _(post)_
- .../admin/hall/{id} _(put, delete)_
- .../admin/hall _(post)_

## ***Used technology***
 - Spring Boot 2
 - Spring Security
 - Spring MVC
 - JWT 
 - Lombok
 - MapStruct
 -  JUnit
 - Mockito
 - Flyway DB