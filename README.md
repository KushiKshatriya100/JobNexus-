# 💼 JobPortal

A full-stack **Job Portal Web Application** built using **Java, JSP, Servlets, JDBC, and MySQL**, designed to connect **job seekers** and **recruiters** in a simple and efficient way.

---

## 🚀 Project Overview

The **JobPortal** web application allows:
- Job seekers to **register, log in, view, and apply** for jobs.
- Recruiters to **post, edit, and manage** job listings.
- Admins to oversee the platform activities.

Built with **Java EE (JSP + Servlet)** architecture and deployed on **Render**, with a **MySQL** database hosted on **Railway**.

---

## 🧰 Tech Stack

| Layer | Technology Used |
|-------|------------------|
| **Frontend** | HTML, CSS, Bootstrap, JSP |
| **Backend** | Java, Servlets, JDBC |
| **Database** | MySQL (Hosted on Railway) |
| **Build Tool** | Maven |
| **Server** | Apache Tomcat |
| **Hosting** | Ngrok Platform |

---

## ✨ Features

### 👨‍💼 For Job Seekers:
- Register and create profile  
- View available jobs  
- Apply for desired jobs  
- Upload resume  

### 🧑‍💻 For Recruiters:
- Post new job listings  
- Manage (update/delete) job posts  
- View applicants for each job  

### 🧭 Admin Panel:
- Manage users and jobs  
- Monitor overall platform activity  

---

## 🗂️ Project Structure
JobPortal/
├── src/
│ ├── main/java/com/parent/
│ │ ├── dao/ # Data Access Layer
│ │ ├── model/ # JavaBeans (Entities)
│ │ ├── servlet/ # Controllers
│ │ └── util/ # DBConnection utility
│ └── main/webapp/
│ ├── jsp/ # JSP Pages
│ ├── css/, js/, images/
│ └── WEB-INF/ # web.xml
├── pom.xml # Maven dependencies
└── README.md # Project documentation


---

## ⚙️ Installation & Setup

Follow these steps to run the project locally 👇

### 1️⃣ Clone the repository
```bash
git clone https://github.com/<your-username>/JobPortal.git
cd JobPortal
