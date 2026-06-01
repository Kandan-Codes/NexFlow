# NexFlow – Employee Task & Workflow Management System

## Overview

NexFlow is a console-based Java application designed to manage employees, assignments, reporting hierarchies, task tracking, notifications, and analytics inside an organization.

The project follows a modular feature-based structure and uses an in-memory repository pattern for storing application data.

The application supports:

- Employee onboarding and authentication
- Role-based access (Manager / Employee)
- Task creation and assignment
- Task status tracking
- Team monitoring
- Notifications
- Reporting and analytics
- Reporting hierarchy management

---

# Project Information

| Property | Value |
|---|---|
| Project Name | NexFlow |
| Language | Java |
| Architecture Style | Feature-based MVC |
| Data Storage | In-memory repository |
| Entry Point | `ThiranXApplication.java` |
| Package Root | `com.aurora.nexflow` |
| Version | 0.0.1 |

---

# High-Level Architecture

The project follows a lightweight MVC-inspired architecture.

## Layers

### 1. View Layer
Responsible for:
- User interaction
- Console input/output
- Menu rendering
- Navigation handling

Examples:
- `SignInView`
- `TaskListView`
- `HomeView`

---

### 2. Model Layer
Responsible for:
- Business logic
- Validation
- Data coordination
- Repository interaction

Examples:
- `SignInModel`
- `TaskAssignModel`
- `ReportModel`

---

### 3. DTO Layer
Responsible for:
- Data transfer
- Entity representation

Examples:
- `Employee`
- `Task`
- `Notification`

---

### 4. Repository Layer
Responsible for:
- Centralized data management
- CRUD operations
- In-memory persistence

Main repository:
- `ThiranXDB`

---

# Project Structure

```text
src/
└── com/
    └── mani/
        └── nexflow/
            ├── NexFlowApplication.java
            │
            ├── util/
            │   ├── ParserToolkit.java
            │   └── TerminalReader.java
            │
            ├── data/
            │   ├── dto/
            │   │   ├── Employee.java
            │   │   ├── Task.java
            │   │   ├── Notification.java
            │   │   ├── LoginRequest.java
            │   │   └── TaskStatusHistory.java
            │   │
            │   └── repository/
            │       └── ThiranXDB.java
            │
            └── features/
                ├── signin/
                ├── signup/
                ├── dashboard/
                ├── alertMessage/
                ├── analyticsReport/
                ├── staffMember/
                └── assignment/
```

---

# Application Flow

## Startup Flow

Application starts from:

```java
ThiranXApplication.main()
```

The user sees:

```text
1. Sign Up
2. Sign In
3. Exit
```

---

# Core Modules

# 1. Authentication Module

Package:

```text
features/signin
features/signup
```

## Features

### Sign Up
Users can:
- Create employee accounts
- Register manager or employee roles
- Provide credentials
- Join the system

### Sign In
Users can:
- Authenticate using email and password
- Access dashboards based on role

---

## Important Classes

| Class | Responsibility |
|---|---|
| `SignUpView` | User input for registration |
| `SignUpModel` | Registration logic |
| `SignInView` | Login interface |
| `SignInModel` | Authentication logic |
| `LoginRequest` | Login DTO |

---

# 2. Dashboard Module

Package:

```text
features/dashboard
```

## HomeView

The dashboard acts as the central navigation hub.

### Manager Dashboard
Managers can:

1. View all employees
2. View reportees
3. Add employees
4. Create tasks
5. Assign tasks
6. View team status
7. Update own task status
8. View task details
9. View notifications
10. View reports
11. Sign out

---

### Employee Dashboard
Employees can:

- View assigned tasks
- Update task status
- Check notifications
- Track assignments

---

## Important Classes

| Class | Responsibility |
|---|---|
| `HomeView` | Dashboard rendering |
| `HomeModel` | Dashboard access logic |

---

# 3. Employee Management Module

Package:

```text
features/staffMember
```

This module manages employee information and reporting hierarchy.

---

## Features

### Employee List
Displays:
- Employee ID
- Name
- Email
- Role
- Status

### Employee Details
Shows:
- Profile information
- Reporting manager
- Status
- Contact details

### Add Employee
Managers can:
- Add employees
- Assign managers
- Set roles

### Reportee Management
Managers can:
- View direct reportees
- Track subordinate employees

---

## Important Classes

| Class | Responsibility |
|---|---|
| `EmployeeListView` | Employee listing UI |
| `EmployeeListModel` | Employee retrieval logic |
| `EmployeeAddView` | Employee creation UI |
| `EmployeeAddModel` | Employee insertion logic |
| `EmployeeDetailsView` | Employee profile screen |
| `EmployeeDetailsModel` | Employee details logic |
| `ReporteeListView` | Reportee listing |
| `ReporteeListModel` | Reporting hierarchy logic |

---

# 4. Assignment / Task Module

Package:

```text
features/assignment
```

This is the core workflow engine of NexFlow.

---

# Task Lifecycle

```text
OPEN
   ↓
IN_PROGRESS
   ↓
COMPLETED
```

Additional statuses:

```text
ON_HOLD
CANCELLED
REOPENED
```

---

## Features

### Task Creation
Managers can:
- Create assignments
- Define title and description
- Set due dates
- Set priority

### Task Assignment
Managers can:
- Assign tasks to employees
- Track assignment ownership

### Task Listing
Users can:
- View all tasks
- Filter relevant assignments

### Task Details
Displays:
- Task information
- Current status
- Remarks
- Timeline

### Task Status Update
Employees can:
- Change task status
- Add remarks
- Mark completion

### Team Status
Managers can:
- Track team productivity
- Monitor assignment progress

---

## Assignment Sub-Packages

| Package | Purpose |
|---|---|
| `create` | Create tasks |
| `assign` | Assign tasks |
| `list` | List tasks |
| `detail` | Task details |
| `status` | Status updates |
| `team` | Team tracking |

---

## Important Classes

| Class | Responsibility |
|---|---|
| `TaskCreateView` | Task creation screen |
| `TaskCreateModel` | Task creation logic |
| `TaskAssignView` | Task assignment UI |
| `TaskAssignModel` | Assignment processing |
| `TaskListView` | Task listing |
| `TaskListModel` | Task retrieval |
| `TaskDetailView` | Detailed task view |
| `TaskDetailModel` | Task detail logic |
| `TaskStatusUpdateView` | Status update screen |
| `TaskStatusUpdateModel` | Status transition logic |
| `TeamStatusView` | Team tracking UI |
| `TeamStatusModel` | Team analytics logic |

---

# 5. Notification Module

Package:

```text
features/alertMessage
```

## Features

The notification system informs users about:

- Task assignments
- Status updates
- Important changes
- Workflow events

---

## Important Classes

| Class | Responsibility |
|---|---|
| `NotificationView` | Notification display |
| `NotificationModel` | Notification logic |
| `Notification` | Notification DTO |

---

# 6. Analytics & Reports Module

Package:

```text
features/analyticsReport
```

## Features

The reporting system provides:

- Task completion summaries
- Employee productivity
- Team tracking
- Assignment statistics

---

## Important Classes

| Class | Responsibility |
|---|---|
| `ReportView` | Report rendering |
| `ReportModel` | Report generation logic |
| `Report` | Report DTO |

---

# Data Layer

Package:

```text
data/dto
```

---

# Employee DTO

Represents an employee.

## Important Fields

| Field | Description |
|---|---|
| `id` | Internal primary key |
| `employeeId` | Generated employee code |
| `name` | Employee name |
| `email` | Login email |
| `accessKey` | Password |
| `mobileNo` | Contact number |
| `dob` | Date of birth |
| `role` | Manager / Employee |
| `reportingTo` | Manager reference |
| `status` | Active / Inactive |
| `createdAt` | Creation timestamp |

---

## Employee Roles

```java
MANAGER
EMPLOYEE
```

---

## Employee Status

```java
ACTIVE
INACTIVE
```

---

# Task DTO

Represents assignments inside the system.

## Important Fields

| Field | Description |
|---|---|
| `id` | Task ID |
| `title` | Task title |
| `description` | Task description |
| `assignedBy` | Manager ID |
| `assignedTo` | Employee ID |
| `priority` | P1 / P2 / P3 |
| `createdTime` | Creation timestamp |
| `dueDate` | Due date |
| `updatedTime` | Last update time |
| `completedTime` | Completion time |
| `remarks` | Additional comments |
| `status` | Task state |

---

## Task Priorities

```java
P1
P2
P3
```

---

## Task Status Values

```java
OPEN
IN_PROGRESS
COMPLETED
ON_HOLD
CANCELLED
REOPENED
```

---

# Notification DTO

Represents alerts generated by the system.

Possible use cases:
- Assignment alerts
- Task reminders
- Status change alerts

---

# TaskStatusHistory DTO

Stores:
- Task status transitions
- Audit tracking
- Workflow progression

---

# Repository Layer

Package:

```text
data/repository
```

---

# ThiranXDB

`ThiranXDB` is a singleton in-memory database.

## Responsibilities

- Store employees
- Store tasks
- Store notifications
- Store task histories
- Generate IDs
- Authenticate users
- Manage application state

---

## Collections Managed

```java
List<Employee>
List<Task>
List<TaskStatusHistory>
List<Notification>
```

---

## Important Features

### Employee ID Generation

```java
EMP00001
EMP00002
```

---

### Authentication

```java
authenticateEmployee(email, password)
```

---

### Active Manager Retrieval

```java
getActiveManagers()
```

---

### Employee Filtering

```java
getEmployeesExcept(id)
```

---

# Utility Layer

Package:

```text
util
```

---

# TerminalReader

Purpose:
- Shared scanner handling
- Console input management

---

# ParserToolkit

Purpose:
- Parsing helper methods
- Input transformation
- Validation support

---

# Design Patterns Used

## 1. Singleton Pattern

Used in:

```java
ThiranXDB
```

Purpose:
- Single shared repository instance

---

## 2. MVC Pattern

Used across feature modules.

### Example

```text
TaskCreateView
TaskCreateModel
Task DTO
```

---

## 3. Feature-Based Packaging

Features are grouped together instead of grouping by technical layer.

Benefits:
- Better modularity
- Easier maintenance
- Better scalability

---

# Access Control

## Manager Permissions

Managers can:

- Add employees
- Create tasks
- Assign tasks
- View reportees
- Track teams
- Generate reports

---

## Employee Permissions

Employees can:

- View tasks
- Update statuses
- Receive notifications
- Track assignments

---

# Example User Journey

## Manager Workflow

```text
Sign Up
   ↓
Sign In
   ↓
Open Dashboard
   ↓
Add Employee
   ↓
Create Task
   ↓
Assign Task
   ↓
Track Team Progress
```

---

## Employee Workflow

```text
Sign In
   ↓
View Assigned Tasks
   ↓
Start Task
   ↓
Update Status
   ↓
Complete Task
```

---

# Strengths of the Project

## Clean Modular Design
Each feature is isolated into dedicated packages.

---

## Good Separation of Concerns
Views, models, DTOs, and repository are separated.

---

## Easy to Extend
Possible future enhancements:

- Database integration
- REST API support
- GUI application
- Web application
- Spring Boot migration
- Authentication tokens
- Persistent storage

---

## Beginner Friendly
The project is suitable for:

- Java learning
- MVC understanding
- Console application practice
- Repository pattern learning
- OOP implementation

---

# Current Limitations

## In-Memory Storage
Data is lost after application shutdown.

---

## No Encryption
Passwords are stored as plain text.

---

## No Database Layer
No JDBC or ORM integration currently exists.

---

## Console-Only Interface
No GUI or web frontend.

---

## No Unit Testing
No automated tests are included.

---

# Recommended Improvements

## Backend Improvements

- Add MySQL/PostgreSQL
- Use JDBC or Hibernate
- Add DAO layer
- Introduce service layer
- Add dependency injection

---

## Security Improvements

- Password hashing
- Session management
- Role authorization middleware
- Input sanitization

---

## Scalability Improvements

- Convert to Spring Boot
- Build REST APIs
- Add JWT authentication
- Introduce logging framework

---

## UI Improvements

- JavaFX frontend
- Web frontend
- React dashboard
- Mobile app integration

---

# How to Run the Project

## Requirements

- Java JDK 8+
- IDE (IntelliJ IDEA recommended)

---

## Steps

### 1. Extract Project

```text
NexFlow_Refactored_Project.zip
```

---

### 2. Open in IDE

Import as a Java project.

---

### 3. Run Main File

```text
src/com/aurora/nexflow/ThiranXApplication.java
```

---

### 4. Start Using Application

```text
1. Sign Up
2. Sign In
3. Exit
```

---

# Recommended Future Architecture

```text
Controller Layer
       ↓
Service Layer
       ↓
Repository Layer
       ↓
Database
```

---

# Conclusion

NexFlow is a well-structured Java console application that demonstrates:

- Object-oriented programming
- MVC architecture
- Repository pattern
- Feature modularization
- Task workflow management
- Employee hierarchy management

The project serves as a strong foundation for evolving into a production-grade workflow management platform.

It is particularly valuable for:

- Java beginners
- Academic projects
- Internship assignments
- Architecture practice
- MVC learning
- Console application development.....
