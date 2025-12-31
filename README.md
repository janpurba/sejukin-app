# ❄️ Sejukin App - AC Service Management System

A web-based application designed to assist field technicians in managing customer data, service history, and automated maintenance reminders. Built with a focus on data isolation (multi-tenancy) and clean architecture.

## 🚀 Key Features

* **Multi-Tenancy Architecture:** Ensures data privacy where each technician (user) can only access and manage their own list of customers.
* **Customer Management:** Complete CRUD (Create, Read, Update, Delete) operations for customer data.
* **Service History Tracking:** Records detailed service logs, pricing, and dates.
* **Smart Auto-Reminder:** Automatically calculates and schedules the next service date (default: 90 days after the last service).
* **Secure Authentication:** Robust login system using **Spring Security** with BCrypt password hashing.
* **Responsive UI:** Built with Thymeleaf and Bootstrap for accessibility on mobile and desktop devices.

## 🛠 Tech Stack

* **Backend:** Java 17, Spring Boot 3
* **Database:** MySQL 8.0
* **Security:** Spring Security, BCrypt
* **Containerization:** Docker, Docker Compose
* **Build Tool:** Maven
* **Architecture:** MVC + Service Layer Pattern

## 📦 How to Run (Using Docker)

The easiest way to run this application is using Docker. Ensure you have **Docker** and **Docker Compose** installed.

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/janpurba/sejukin-app.git](https://github.com/YOUR_USERNAME/sejukin-app.git)
    cd sejukin-app
    ```

2.  **Build the JAR File**
    ```bash
    ./mvnw clean package -DskipTests
    ```
    *(Note: On Windows CMD, use `mvnw clean package -DskipTests`)*

3.  **Run with Docker Compose**
    ```bash
    docker-compose up --build
    ```

4.  **Access the Application**
    Open your browser and navigate to: `http://localhost:8080/pelanggan`

## 💻 How to Run (Local / Manual)

If you prefer running it without Docker:

1.  Ensure **MySQL** is running on your machine.
2.  Create a database named `sejukin_db`.
3.  Update `src/main/resources/application.properties` with your MySQL credentials.
4.  Run the app:
    ```bash
    ./mvnw spring-boot:run
    ```

## 🔑 Default Credentials

Use this account to log in for the first time:

* **Username:** `admin`
* **Password:** `admin123`

---
*Developed by janpurba*# sejukin-app
