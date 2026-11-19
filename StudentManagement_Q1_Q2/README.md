# Student Profile and Skill Development Management System (Q1 + Q2)

This is the expanded IntelliJ IDEA / Maven / Spring Boot project for Q1(a) and Q2(a).

## Requirements
- JDK 17 or later
- IntelliJ IDEA
- MySQL Server 8.0.16+ (for CHECK constraints)
- Internet connection for the first Maven import

## 1. Create the MySQL database
Open MySQL Workbench or the MySQL command line and run:

```sql
CREATE DATABASE student_management;
```

The application creates/updates the tables when it starts (`spring.jpa.hibernate.ddl-auto=update`).
The equivalent table design is also documented in `database/schema.sql`.

## 2. Configure your MySQL username and password
Open `src/main/resources/application.properties`.

Default values are:
- username: `root`
- password: `password`

Change these to your own MySQL credentials. Alternatively, set environment variables
`DB_USERNAME` and `DB_PASSWORD`.

## 3. Import into IntelliJ IDEA
1. Extract the ZIP.
2. In IntelliJ IDEA choose **File → Open**.
3. Select the extracted `StudentManagement_Q1_Q2` folder (the folder containing `pom.xml`).
4. Trust the project if prompted and allow IntelliJ to load Maven dependencies.
5. Set Project SDK to JDK 17: **File → Project Structure → Project → SDK**.

## 4. Run the application
Run `StudentmanagementApplication.java` using the green Run icon.
The server starts at `http://localhost:8080`.

## 5. Try the API
Use Postman, IntelliJ HTTP Client, or curl. Example request:

```http
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "studentId": 101,
  "studentName": "Aarav Sharma",
  "emailAddress": "aarav@example.com",
  "courseName": "BTech IT",
  "programmingScore": 88,
  "mathematicsScore": 92,
  "technicalSkills": ["Java", "Python", "SQL"]
}
```

Then try:
- `GET /api/students` — all students
- `GET /api/students/101/performance` — average and category
- `GET /api/students/performance` — all students with average and category
- `GET /api/students/stronger-subject` — stronger subject for each student
- `GET /api/students/search?keyword=aarav` — search by numeric ID, email, or course
- `GET /api/students/merit-list` — descending average
- `GET /api/students/shared-skills` — groups of students sharing skills
- `GET /api/students/programming-improvement` — programming below 50 and mathematics at least 75

## Q2 requirement mapping
1. Average score: `(programmingScore + mathematicsScore) / 2`
2. Category: Excellent (>=85), Good (>=70), Satisfactory (>=50), Needs Improvement (<50)
3. Stronger subject: compares programming and mathematics scores
4. Search: by exact numeric ID or partial email/course match
5. Merit list: sorted by average descending
6. Shared skills: groups students by each technical skill shared by 2 or more students
7. Programming improvement: assumes "needs improvement in programming" means programming score below 50; mathematics must be >=75
8. Duplicate IDs: `studentId` is the primary key; scores are validated from 0 to 100 in request validation and database constraints.

## Important
Q2 does not explicitly define the threshold for "needs improvement in programming".
This implementation uses **programming score below 50**, consistent with the overall "Needs Improvement" category.


## Q3(a) REST API endpoints

The same project now includes all six REST API operations requested in Q3:

| Q3 requirement | HTTP method and endpoint |
|---|---|
| Add a student | `POST /api/students` |
| Retrieve all students | `GET /api/students` |
| Search for a student by ID | `GET /api/students/{id}` |
| Update scores and skills only | `PUT /api/students/{id}/scores-skills` |
| Delete a student record | `DELETE /api/students/{id}` |
| Display the merit list | `GET /api/students/merit-list` |

### Example requests

**1. Add a student**

```http
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "studentId": 101,
  "studentName": "Aarav Sharma",
  "emailAddress": "aarav@example.com",
  "courseName": "BTech IT",
  "programmingScore": 88,
  "mathematicsScore": 92,
  "technicalSkills": ["Java", "Python", "SQL"]
}
```

**2. Retrieve all students**

```http
GET http://localhost:8080/api/students
```

**3. Search by ID**

```http
GET http://localhost:8080/api/students/101
```

**4. Update scores and skills**

```http
PUT http://localhost:8080/api/students/101/scores-skills
Content-Type: application/json

{
  "programmingScore": 95,
  "mathematicsScore": 91,
  "technicalSkills": ["Java", "Spring Boot", "MySQL"]
}
```

**5. Delete a student**

```http
DELETE http://localhost:8080/api/students/101
```

**6. Display merit list**

```http
GET http://localhost:8080/api/students/merit-list
```

You can send these requests using Postman or IntelliJ IDEA's HTTP Client. Start the Spring Boot application first and ensure MySQL is running and the database credentials in `application.properties` are correct.
