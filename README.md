# ❄️ Sejukin App - AC Service Management System

A web-based application designed to assist field technicians in managing customer data, service history, and automated maintenance reminders. Built with a focus on data isolation (multi-tenancy) and clean architecture.

## 🚀 Key Features

* **Multi-Tenancy Architecture:** Ensures data privacy where each technician (user) can only access and manage their own list of customers.
* **Customer Management:** 
    * Complete CRUD (Create, Read, Update, Delete) operations.
    * **WhatsApp Validation:** Ensures phone numbers are valid Indonesian numbers connected to WhatsApp.
    * **Quick Edit:** Update customer contact details directly from the list.
* **Service History Tracking:** 
    * Records detailed service logs, pricing, and dates.
    * View complete service history per customer.
* **Smart Auto-Reminder:** 
    * Automatically calculates and schedules the next service date (default: 90 days after the last service).
    * **Dashboard Overview:** Monitor pending and sent reminders.
* **Dashboard Analytics:** Real-time overview of total customers, total services performed, and reminder statuses.
* **Secure Authentication:** 
    * Robust login system using **Spring Security** with BCrypt password hashing.
    * **User Status Validation:** Only active users can access the system.
    * **Role-Based Access Control:** Flexible role management via Lookup tables.
* **Responsive UI:** Built with Thymeleaf and Bootstrap for accessibility on mobile and desktop devices.

## 🛠 Tech Stack

* **Backend:** Java 17, Spring Boot 3
* **Database:** MySQL 8.0
* **Caching:** Redis
* **Security:** Spring Security, BCrypt
* **Containerization:** Docker, Docker Compose
* **Build Tool:** Maven
* **Architecture:** MVC + Service Layer Pattern

## 📦 How to Run (Using Docker)

The easiest way to run this application is using Docker. Ensure you have **Docker** and **Docker Compose** installed.

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/janpurba/sejukin-app.git
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
    Open your browser and navigate to: `http://localhost:8080/auth/login`

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
*Developed by janpurba*
