
# Spring Boot Web Application - Записна книжка

## Introduction

This Spring Boot web application serves as a digital notebook for managing contact information and personal details of acquaintances, colleagues, and relatives. It allows users to store and organize data such as name, surname, email, address, phone numbers, workplace or education details, relationship nature, and different qualities.

## Features

- **Contact Management:** Store and manage contact details of acquaintances, colleagues, and relatives.

- **Automatic Birthday Greetings:** Automatically generates birthday greetings.

- **Sorting:** Sort contacts alphabetically or by the date of the last modification.

- **Search:** Search contacts based on specific criteria or using different patterns


# Run application 

### 1. Clone repository

### 2. Database Setup

This application uses PostgreSQL as the database. Before running the application, ensure that PostgreSQL is installed on your system and create a database for the application. Follow these steps:

Install my-project with npm

- **Install PostgreSQL:** If PostgreSQL is not already installed, download and install it from the official website: https://www.postgresql.org/download/.

- **Create Database:** Create a new database for the application using the PostgreSQL command-line interface or a graphical tool like pgAdmin. 

```bash
CREATE DATABASE address_book_gfl;
```

- **Run SQL Script:** Execute the provided SQL script (```postgresql.sql```) (https://github.com/ValeriiaHuza/final_addressbook_huza.git) against the newly created database to set up the required tables and initial data. 

- **Change Database Configuration:** Open the ```application.yml``` file in the Spring Boot application's ```src/main/resources``` directory. Update the database connection properties ```spring.datasource.url, spring.datasource.username, spring.datasource.password``` to match your PostgreSQL configuration.
    
### 3. Run the application ``` mvn clean spring-boot:run```

### 4. Open browser : http://localhost:8080 
