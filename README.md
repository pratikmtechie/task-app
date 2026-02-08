# Task Management Application

Full-stack task management app built with:
- Angular (Frontend)
- Spring Boot (Backend)
- REST API

## Project Structure
frontend/ - Angular application
backend/ - Spring Boot application


## How to Run Locally

### Backend
```bash
cd backend
./mvnw spring-boot:run

### Frontend
cd frontend
npm install
ng serve

Frontend runs at: http://localhost:4200
Backend runs at: http://localhost:8080

Features
- Create tasks
- List tasks
- Filter by status


Key architectural decisions along with reasons
	- Layered Architecture with three layers Controller, Service & Repository. This was done to maintain sepaaration of concerns & follow SOLID pricnciples.
	- Builder Design Patterns used wherever needed to create legible & concise code.
	- lombok extensions used to reduce the boiler plate code.
	- Log4j2 framework used for logging
	- Interface based programming model used to maintain loose coupling between application components

Tradeoffs considered
	- Basic UI developed to serve the purpose of Task Management application instead of more elegant UI to save on time.
	- Login & Logout functionality was omitted due to time constraints but that is the first thing to be addressed if additional time is given.
	- ConcurrentHashMap is used as In-Memory Database instead of H2 DB. This was solely done to save on configuration time.
	- JSR 380  validation framework used to quickly validate incoming requests instead of writing custom validation framework. Custom validation framework gives more control over error handling. But considering the deadline for completion we have used ready made solution.  
