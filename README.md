
# 💼 JobPortal (JobNexus)

A **Java Web Application** built using **JSP, Servlets, JDBC, and MySQL**, following a clean **MVC-style Maven project structure**.  
This project is ideal for **MCA final-year submission, placements, and Java backend interviews**.

---

## 🎯 Project Goal (Structure First)

This project demonstrates:
- Standard **Maven directory layout**
- Clear separation of **Controller, DAO, Model, Utility, and View layers**
- Real-world **Servlet → DAO → DB → JSP** request flow
- Generation of a deployable **WAR file** for Apache Tomcat

> 📌 Focus of this README: **Project Structure & Architecture**

---

## 🧱 Technology Stack

| Layer | Technology |
|-----|-----------|
| Frontend | JSP, HTML, CSS |
| Backend | Java, Servlets |
| Database | MySQL |
| Persistence | JDBC |
| Build Tool | Maven |
| Server | Apache Tomcat |

---

## 📁 Project Structure (Maven Standard)

```
JobNexus-
│── pom.xml                     # Maven dependencies & build config
│── .gitignore                  # Git ignore rules
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── parent/
│       │           ├── controller/        # Servlet Controllers
│       │           │   ├── LoginServlet.java
│       │           │   ├── RegisterServlet.java
│       │           │   ├── JobsServlet.java
│       │           │   ├── ApplyServlet.java
│       │           │   ├── PostJobServlet.java
│       │           │   ├── ManageJobsServlet.java
│       │           │   ├── ProfileServlet.java
│       │           │   └── LogoutServlet.java
│       │           │
│       │           ├── dao/               # JDBC Database Layer
│       │           │   ├── UserDAO.java
│       │           │   ├── JobDAO.java
│       │           │   ├── ApplicationDAO.java
│       │           │   └── CandidateProfileDAO.java
│       │           │
│       │           ├── model/             # POJO / Entity Classes
│       │           │   ├── User.java
│       │           │   ├── Job.java
│       │           │   ├── Application.java
│       │           │   └── CandidateProfile.java
│       │           │
│       │           └── util/              # Utility Classes
│       │               └── DBConnection.java
│       │
│       └── webapp/
│           ├── jsp/                       # JSP View Pages
│           │   ├── index.jsp
│           │   ├── login.jsp
│           │   ├── register.jsp
│           │   ├── jobs.jsp
│           │   ├── apply.jsp
│           │   ├── profile.jsp
│           │   └── recruiter_dashboard.jsp
│           │
│           └── WEB-INF/
│               └── web.xml               # Servlet mappings
│
└── target/
    ├── JobPortal.war                     # Deployable WAR file
    └── classes/                          # Compiled .class files
```

---

## 🏛️ Architecture Explanation

### 🔹 Controller Layer (`controller`)
- Contains **Servlet classes**
- Handles HTTP requests (`doGet`, `doPost`)
- Controls navigation between JSP pages
- Communicates with DAO layer

**Example:** `LoginServlet`, `PostJobServlet`

---

### 🔹 DAO Layer (`dao`)
- Handles **all database operations**
- Uses JDBC with PreparedStatements
- Keeps SQL logic separate from Servlets

**Example:** `UserDAO`, `JobDAO`

---

### 🔹 Model Layer (`model`)
- POJO classes representing database tables
- Contains fields, constructors, getters, and setters

**Example:** `User`, `Job`, `Application`

---

### 🔹 Utility Layer (`util`)
- Common reusable logic
- Manages database connection handling

**Example:** `DBConnection.java`

---

### 🔹 View Layer (`jsp`)
- JSP pages for UI rendering
- Receives data via request/session attributes

---

## 🔄 Request Flow (Interview Explanation)

```
Browser
   ↓
JSP Form
   ↓
Servlet (Controller)
   ↓
DAO (JDBC)
   ↓
MySQL Database
   ↑
Servlet
   ↑
JSP Response
```

---

## 📦 Build & Deployment

```bash
mvn clean package
```

- Generates `JobPortal.war`
- Deploy WAR on **Apache Tomcat**
- Access via `http://localhost:8080/JobPortal`

---

## 🚀 Future Enhancements

- Add Service Layer
- Migrate to Spring MVC / Spring Boot
- Integrate Spring Security
- Replace JSP with Thymeleaf

---

📌 *Servlet-based system design.*
