# Blog App (APIs)
Live: https://blogapp.up.railway.app/swagger-ui/index.html

Here are the backend APIs of a Blog Application. There two kinds of user - Admin and Normal User. Admin can access all the APIs including DELETE APIs. Normal user can not access DELETE APIs, but can access other APIs.

## Tools And Technology
* Programming Language: Java 17
* Spring Boot: 3.0.1
* Build Tool: Maven
* Database: PostgreSQL

## Features & Services
* User Registration and Login
* Update User Information
* REST API (User)
* REST API (Category)
* REST API (Post)
* Validation for Creation and Updating
* JWT Authentication
* Role Based Authorization
* API Documentation with Open API-3 (Swagger UI)

## User Manual
Go to the Live Link which I provided above. You'll get a fantastic UI provided by Swagger. You can test the APIs there. Before doing registration, you are just a guest and so you can access only GET APIs. After that, please register with your email, password and description to create post. Now, do login with authentic username and password to access other APIs. You can also do comments on other posts. Remember, your email is your username. After a successful authentication, you can create category, post and manage them. One more thing is as a normal registerd user, you can not access the DELETE APIs. Thank You.  


