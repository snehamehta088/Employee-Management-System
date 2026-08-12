# Employee Management System

A desktop-based **Employee Management System** developed using **Java Swing, JDBC, and SQLite**. The application provides a simple and user-friendly interface to manage employee records.

## Features

* Add new employees
* View all employee records
* Update employee information
* Delete employee records
* Search employees by name, email, or department
* Clear input fields
* SQLite database integration
* User-friendly graphical interface

## Technologies Used

* **Java**
* **Java Swing** – Graphical User Interface
* **JDBC** – Database connectivity
* **SQLite** – Database
* **VS Code** – Development Environment

## Employee Details

The application stores:

* Employee ID
* Name
* Email
* Phone
* Department
* Salary

## Project Structure

```text
Employee-Management-System
│
├── .vscode
│   └── settings.json
│
├── lib
│   └── sqlite-jdbc-3.53.2.1.jar
│
└── src
    ├── DatabaseConnection.java
    ├── DatabaseSetup.java
    ├── Employee.java
    ├── EmployeeDAO.java
    └── EmployeeManagementSystem.java
```

## How to Run

1. Clone or download the repository.
2. Make sure Java is installed on your system.
3. Ensure the SQLite JDBC JAR is available in the `lib` folder.
4. Compile the Java source files with the SQLite JAR in the classpath.
5. Run `EmployeeManagementSystem.java`.

Example:

```powershell
java -cp ".;lib/*;src" EmployeeManagementSystem
```

The SQLite database will be created automatically when the application runs.

## Database

The project uses **SQLite**, so no separate MySQL server is required.

The database stores employee records in an `employees` table.


## Future Improvements

* Employee login and authentication
* Role-based access
* Export employee records to PDF/Excel
* Advanced filtering
* Employee profile management

## Author

**Sneha Mehta**

This project was developed as part of a **Java Developer Internship**.
