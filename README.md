# PokéSphere
Welcome to the **Pokesphere**, built with modern Android development tools, following best practices for architecture, dependency injection and UI design with Jetpack Compose.
Pokesphere is a project built by Julio Martins as a showcase of abilities in android software development. 
It still have room for improvements and I will adopt new technologies or apply any code refinement to improve performance, maintainability and design.


## 🚀 Features
- **Modern UI:** Jetpack Compose with Material 3
- **Clean Architecture:** Modular design for scalability
- **Reactive Programming:** Kotlin Coroutines for asynchronous operations
- **Efficient Data Handling:** Room for local database, Retrofit & OkHttp for networking
- **Dependency Injection:** Dagger Hilt
- **Media:** Glide for image loading
- **Custom Icons:** FontAwesome Compose integration

---

## 🛠️ Tech Stack

### **UI**
- **Jetpack Compose** - Declarative UI toolkit
- Material Design 3 for modern components
- FontAwesome Compose for icons

### **Architecture**
- MVVM (Model-View-ViewModel)
- Jetpack Lifecycle for managing UI lifecycle
- AndroidX Core for enhanced functionality

### **Data Handling**
- **Room** for caching data improving data load performance 
- **Retrofit** + **OkHttp** for network requests
- **Gson** for JSON parsing

### **Dependency Injection**
**Dagger Hilt** - Simplified DI framework

### **Asynchronous Programming**
- Kotlin Coroutines

### **Logging**
- Timber for flexible logging

### **Image Loading**
- Glide with Compose integration

---

## 📦 Project Modules
- **UI Layer:** Jetpack Compose components
- **Data Layer:** Repositories for API data requests and Room caching; APIDataSources for Retrofit and Network communication
- **Domain Layer:** Data request and error transformation logic
- **Local Data Layer:** All database files related to caching data from successful requests

---

## 📄 License
**© 2025 [Julio Martins](https://github.com/bricklytics)**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.



