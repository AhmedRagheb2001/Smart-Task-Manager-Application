# Smart-Task-Manager-Application
# 🧠 Smart Task Manager

Smart Task Manager is a **multi-user desktop application** built with **JavaFX** that allows users to collaboratively create, manage, and track tasks in real-time. With a responsive GUI, real-time networking, functional programming features, and persistent storage, it's designed to boost productivity and coordination.

---

## 🚀 Features

### 🎨 JavaFX GUI
- Clean, interactive dashboard with `TableView`
- Add, edit, delete, and search tasks
- Priority & due date selection using dropdowns and pickers
- Deadline alerts and notifications

### 🧠 Functional Programming
- Filter tasks by:
  - Priority (High, Medium, Low)
  - Status (Pending, Completed)
  - Overdue (automatically detected)
- Stream-based sorting and task analysis

### 🌐 Real-Time Collaboration
- Multi-user support via Java Sockets
- Task updates instantly broadcasted to all users
- Basic authentication via name input

### ⚙️ Concurrency
- Background threads for smooth communication
- Thread-safe data handling using synchronization
- Efficient thread management with `ExecutorService`

### 🗃️ Database Integration
- Persistent task storage using **JDBC + MySQL/SQLite**
- Tasks are loaded on startup and saved automatically

---

## 🛠️ Tech Stack

| Layer         | Technology        |
|---------------|-------------------|
| UI            | JavaFX            |
| Language      | Java              |
| Networking    | Java Sockets      |
| Concurrency   | Threads, ExecutorService |
| Functional    | Java Streams, Lambdas |
| Database      | MySQL / SQLite via JDBC |

---

## 📂 Project Structure

