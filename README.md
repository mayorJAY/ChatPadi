<p align="center">
  <img src="app_icon.png" title="App Logo">
</p>

# ChatPadi

ChatPadi is a chat application that connects two Users. It is based on the MQTT Protocol and Publish/Subscribe pattern; it takes two user IDs and parses them as the 
topic to subscribe to and it publishes to one of the topics (the main user). It takes implemented using Clean Architecture, Model-View-ViewModel pattern (MVVM) and 
uses Modern Android Development pattern and libraries.

## Project Characteristics

This project makes use of the following tools and solutions:

* 100% Kotlin
* Modern architecture (Clean Architecture, Model-View-ViewModel)
* [Android Jetpack](https://developer.android.com/jetpack)
* A single-activity architecture (using the Navigation component to manage Fragment operations)
* Dependency Injection
* Material design

## Tech Stack

Min API level is set to 21, so the presented approach is suitable for over 94.1% of devices running Android as at the time of writing this

* [Eclipse Paho Android Service](https://www.eclipse.org/paho/clients/android/) used for the implementations of the MQTT Protocol
* [HiveMQ](https://www.hivemq.com/): the Broker used used for chat connection
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) used to store and manage UI-related data in a lifecycle conscious way
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) which is an observable data holder class used to handle data in a lifecycle-aware manner
* [View Binding](https://developer.android.com/topic/libraries/view-binding) used to easily write code that interacts with views by referencing them directly
* [Koin](https://doc.insert-koin.io/#/) a pragmatic lightweight dependency injection framework used in Kotlin
* [Navigation Component](https://developer.android.com/guide/navigation) used to implement navigation and to ensures a consistent and predictable user experience 
while navigating across screens
