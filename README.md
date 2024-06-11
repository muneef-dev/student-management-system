# Student Management System

The Student Management System is a comprehensive application developed using Java, JavaFX, and MySQL, aimed at streamlining administrative and operational tasks within educational institutions. It prioritizes user-friendly interfaces and efficient programming to effectively meet the needs of schools and universities.

## Table of Contents

- [Introduction](#introduction)
- [Functionality](#functionality)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Dependencies](#dependencies)
- [Authors](#authors)

## Introduction

The Student Management System is a standalone application developed to automate various administrative and operational tasks within an educational institution. Its functionalities include student management and course scheduling. The system aims to provide a user-friendly interface and efficient programming to meet the educational institution's needs effectively.

## Functionality

### User Authentication and Authorization

- Implement a secure login system for school staff with role-based access control.
- Differentiate access levels for administrators, teachers, and other staff members.

### Student Management

- Allow the registration of new students with essential details such as name, contact, address, phone number, etc.
- Provide the ability to update and maintain existing student information.
- Generate and manage unique student IDs.
- Search for existing students by ID or name.

### Course Scheduling

- Implement a user-friendly interface for scheduling and managing courses.
- Enable staff to view available course slots and assign teachers to courses.
- Allow course rescheduling and cancellation by staff.

### Security Measures

- Implement secure password policies and mechanisms for user authentication.

## Usage

To use this application:

1. Clone the repository to your local machine.
2. Import the project into your preferred Java IDE.
3. Set up a MySQL database using the provided SQL scripts.
4. Modify the database connection details in the configuration files.
5. Build and run the application.
6. Access the application through the provided GUI and explore its functionalities.

## Database Schema

The database schema consists of several entities including User, Patient, Department, Doctor, Nurse, Appointment, Room, Diagnosis, Admission, Inventory Item, Medication, and Bill. Each entity has specific attributes and relationships defined within the schema.

## Dependencies

This project utilizes the following dependencies:

- MySQL Connector/J: JDBC driver for connecting Java applications to MySQL databases.
- JFoenix: JavaFX Material Design Library.
- JavaMail API: Java API for sending and receiving email.
- Lombok: Java library to reduce boilerplate code.

## Authors

This project was developed by [@Muneef-dev](https://github.com/muneef-dev).
