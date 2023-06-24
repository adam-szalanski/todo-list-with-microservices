# Project overview

This project consists of 3 microservices working together to provide ToDo list CRUD functionality with Login/Registration and Exception Handling. 
All the love in this project went to Backend microservice whilst Frontend is more of a proof of concept and has a few major flaws.

## Functionality

### Backend
Backend allows users to register and login into the application using Spring Security 6 also providing the CRUD functionality for ToDo tasks for every user.
It also sets up the PostgreSQL database (configured in docker-compose.yml) using liquibase migrations, provides Exception Handling
with customized exception responses, ability to sort and filter ToDo lists using JPA Specifications as well DTO-level Validation and unit tests for creation and updating ToDo tasks.
Additionally Backend provides an endpoint allowing to check how many requests did it handle since launch (requests counted using Actuators HttpExchangeRepository).

### Frontend
Frontend displays the Gui using Thymeleaf templates and sends requests to backend upon entering specific URLs. 
It requires backend to be constantly working in the background since the requests count handled by backend is displayed in the index page footer.

### Gateway
Gateway is here only to route requests for frontend or backend based on the request path.

## Dependencies

The project has dependencies on the following frameworks, libraries, and tools:

### Backend
- Spring Framework: Used for creating RESTful APIs and handling HTTP requests.
- Lombok: Used for generating boilerplate code, such as getters and setters, reducing the amount of manual coding.
- MapStruct: Used for object mapping between DTOs and response objects, simplifying the conversion process.
- Actuator: Used for counting the amount of handled requests.
- Spring Security: Used for login and registration functionality using JWT Tokens and also secures endpoints which require user to be logged in.
- Validation: Provides data validation so that the correct data would be passed to database.
- PostgreSQL driver: Used to work with Postgres database.
- jjwt-api, jjwt-impl, jjwt-jackson - Used for JWT Token logic.

### Frontend
- Spring Framework: Used as a starter for Frontend service.
- Thymeleaf: Used to display prepared .html templates and connect them with Java logic.
- Thymeleaf-extras java8time: Used to display date formatted using thymeleaf.

### Gateway
- Spring Framework: Used as a starter for Gateway service and Spring Gateway.
- Spring Gateway: Used to route requests for frontend or backend based on the request path


## Author
<table>
<tr>
<td><p align="center">Adam Szałański</p></td>
</tr>
<tr>
<td>
<a href="https://linkedin.com/in/adam-szalanski">
   <img width="300px" height="300px" src="https://media.licdn.com/dms/image/D4D03AQEQQN-CcweVoQ/profile-displayphoto-shrink_800_800/0/1675129015106?e=1691625600&v=beta&t=uvJfoD4nDRJDEpLwzY0u2_ldEXJWMgbcx0iu_FOu7aI" alt="Adam Szałański">
  </a>
</td>
</tr>
<tr>
<td align="center">

[![LinkedIn][linkedin-shield]][linkedin-url-adam]

</td>
</tr>
</table>

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url-adam]: https://linkedin.com/in/adam-szalanski
