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
- Ktor serialization
- REST API with JSON support

## 📡 Backend API

This Android project uses a custom RESTful API built with Ktor.

🔗 [BorutoServer (Ktor backend)](https://github.com/Heroid-Dev/BorutoServer)

---
## 🚀 Running the Server Locally

Make sure you have **JDK 17+** and **Gradle** installed.

```bash
# Step into the backend folder
cd backend

# Run the Ktor server
./gradlew run
