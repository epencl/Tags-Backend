# Tags Backend

-Done by Edi Pencl

This is a spring boot app, in order to run it you need Maven and java 8 se instaalled. And it uses the h2 in-memory database.
Testing can be done using the postman collection provided in the root folder.

# Instructions to run

Build project:
mvn clean install 

Then within target folder execute the following command:
java -jar Tags-Backend-0.0.1-SNAPSHOT.jar

Import Tags-Backend.postman_collection.json collection into Postman

# Endpoints

  - (POST) localhost:8080/register - Create user
  - (POST) localhost:8080/login - Get jwt token using user credentials
  - (POST) localhost:8080/links - Add link and get suggested tags
  - (GET) localhost:8080/links - Get links for current user
  - (POST) localhost:8080/tags/{tag} - Add tag to existing link

