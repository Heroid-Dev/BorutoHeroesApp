# Boruto Heroes App

A modern Android application that showcases Boruto anime characters and content. Built using **Kotlin** and **Jetpack Compose** for the frontend and **Ktor** for the backend API. The app provides a clean, fast, and responsive user experience with a fully reactive UI and efficient API communication.

## ✨ Features

- Display a list of Boruto anime characters with images and details
- Smooth UI with Jetpack Compose and Material Design 3
- backend built with Ktor (serving data via RESTful API)
- Support for dark mode and adaptive themes
- Pagination and lazy loading for character list
- Character search functionality
- MVVM architecture with clean code practices
- Dependency Injection with Hilt
- Offline caching with Room (if applicable)

## 🛠️ Tech Stack

**Frontend (Android)**:
- Kotlin
- Jetpack Compose
- Material 3
- ViewModel, LiveData / StateFlow
- Hilt for DI
- Retrofit (for HTTP client)
- Coil (for image loading)

**Backend**:
- Ktor (Kotlin server framework)
- Exposed (Kotlin SQL framework)
- Ktor serialization
- REST API with JSON support

## 📦 Structure  
backend/
├── routes/ # Route definitions for APIs
├── models/ # Data models for serialization
├── controllers/ # Request handlers and business logic
├── database/ # DB configuration, DAO or repository
├── plugins/ # CORS, Serialization, Logging plugins
├── Application.kt # Main entry point of the Ktor server
├── resources/ # application.conf and static files
└── build.gradle.kts # Gradle build script

---
## 🚀 Running the Server Locally

Make sure you have **JDK 17+** and **Gradle** installed.

```bash
# Step into the backend folder
cd backend

# Run the Ktor server
./gradlew run
