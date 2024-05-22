# Chat app 
# Objective
The primary objective of this chat application is to provide a real-time communication platform where users can send and receive messages instantly. The application aims to demonstrate the use of modern Android development practices, including MVVM architecture, Clean Architecture principles, and technologies such as WebSocket for real-time communication, Room for local data storage, and Kotlin Coroutines for asynchronous programming. The application should be robust, maintainable, and scalable, allowing easy addition of new features and improvements.
# Technologies used
- Kotlin
- Websocket Okhttp
- Room Database
- Dependency Injection(Dagger Hilt)
- MVVM Architecture
- clean code
- Coroutin
- DataBinding
# Application Architecture and Design Decisions
1-Application Architecture:
The application follows the Model-View-ViewModel (MVVM) architecture pattern, combined with Clean Architecture principles to ensure separation of concerns and maintainability. The architecture consists of the following components:
- Presentation Layer (View): This layer contains Activities, Fragments, and ViewModels. Activities and Fragments represent the UI components, while ViewModels manage the UI-related data and interactions. LiveData and Data Binding are used for communication between the View and ViewModel.
- Domain Layer: This layer contains the business logic of the application. It consists of use cases that represent the actions a user can perform in the app. Use cases are independent of any specific UI framework and are testable in isolation.
- Data Layer: This layer is responsible for data management and communication with external data sources such as a database, network, or repository. It includes repositories that abstract the data sources and data access objects (DAOs) for database operations.
2. Design Decisions:
- Dependency Injection: Dagger Hilt can be used for dependency injection to provide dependencies to different layers of the application. This promotes modularity and facilitates unit testing.
- Coroutines: Kotlin Coroutines are used for asynchronous programming to perform long-running tasks such as network requests and database operations. Coroutines provide a concise and efficient way to handle asynchronous tasks without blocking the main thread.
- Room Database: Room is used as the local database for caching data and providing offline support. It offers a simplified API for working with SQLite databases in Android and integrates seamlessly with LiveData and Coroutines.
- WebSocket: OkHttp WebSocket can be used for WebSocket communication in real-time applications such as chat apps. WebSocket enables bidirectional communication between the client and server, allowing instant messaging and real-time updates.
# Instructions for Building and Running the Application
1. Prerequisites:
- Android Studio installed on your development machine.
- Emulator or physical Android device for testing.
2. Building the Application:
- Clone the repository containing the source code of the application.
- Open the project in Android Studio.
- Ensure that the necessary dependencies are resolved by syncing the project with Gradle.
3. Running the Application:
- Connect your Android device to the development machine or start an emulator from Android Studio.
- Select the target device from the deployment target dropdown in Android Studio.
- Click on the "Run" button to build and deploy the application to the selected device.
4. Testing the Application:
- Once the application is installed on the device, open it from the app launcher.
- Navigate through the different screens and features to verify the functionality.
  Perform various actions such as sending messages, refreshing data, and navigating between screens to ensure everything works as expected.
# Demo
https://github.com/walidsalah19/chat-app-using-websock/assets/67799939/48efb1bb-0c8a-42c1-a4f5-690c2b3bff68

