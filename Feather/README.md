# WeChat-Inspired Social Android App

A feature-rich, Android social messaging application inspired by WeChat, built natively with Java. This project serves as a comprehensive exercise in Android development, covering UI/UX design, fragment management, and real-time bidirectional communication.

## 📖 Table of Contents

- [Features](#-features)
- [Tech Stack & Dependencies](#-tech-stack--dependencies)
- [App Architecture](#-app-architecture)
- [Installation & Setup](#-installation--setup)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Socket Implementation Notes](#-socket-implementation-notes)
- [License](#-license)

## ✨ Features

- **User Authentication:** A sleek `LoginActivity` for user sign-in
- **Modern Main Interface:** A `MainActivity` with bottom navigation containing three core fragments:
  - `HomeFragment`: Main chat list/discovery feed
  - `ContactFragment`: Friends and contact management
  - `MeFragment`: User profile and settings
- **Real-time Chat:** Full-featured `ChatActivity` supporting:
  - Text messages
  - Image sharing
  - Real-time message delivery
- **Dual Socket Architecture:** Implemented using both:
  - `java.io.Socket` (Blocking I/O)
  - `java.nio.ServerSocket` (Non-blocking I/O)

## 🛠 Tech Stack & Dependencies

- **Language:** Java
- **Minimum SDK:** Android 10.0 (API 29)
- **Core Android Components:**
  - `Activity` - For screen-level UI containers
  - `Fragment` - For modular, reusable UI components
  - `Intent` - For navigation and inter-component communication
  - `RecyclerView` - For efficient list display of chats and contacts
- **Image Loading:** [Glide](https://github.com/bumptech/glide) - For efficient image handling
- **Network Communication:** Custom socket implementation
- **Icons & Assets:** Material Design Icons from [Pictogrammers](https://pictogrammers.com/library/mdi/)
- **Architecture:** Standard Android Architecture Components

## 🏗 App Architecture
