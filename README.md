# Plateful 🍽️

## Overview  
Plateful is a modern food discovery and ordering application that provides a seamless user experience for browsing, selecting, and purchasing meals. Built with an intuitive UI and integrated with Firebase for authentication and analytics.  

## Installation  
To set up the project locally:  
1. Clone the repository:  
   ```sh
   git clone https://github.com/habibaelhadi/Plateful.git
2. Open the project in Android Studio.
3. Ensure you have the latest Gradle and Android SDK installed.
4. Sync the project with Gradle files.
5. Run the application on an emulator or physical device.

## Prerequisites & Dependencies
Plateful is built using the following libraries and frameworks:
###  Core Dependencies
#### Androidx
* androidx.appcompat – Compatibility support for modern UI components.
* androidx.constraintlayout – Optimized layouts for UI design.
* androidx.activity – Activity lifecycle management.
* androidx.navigation:navigation-fragment:2.5.3 – Fragment navigation.
* androidx.navigation:navigation-ui:2.5.3 – UI-based navigation control.
* androidx.room:room-runtime:2.6.1 – Local database storage.
* androidx.room:room-rxjava3:2.4.1 – RxJava support for Room.
* annotationProcessor("androidx.room:room-compiler:2.6.1") – Room database annotation processor.

#### Google Services & Firebase
* com.google.firebase:firebase-bom:33.8.0 – Firebase platform.
* com.google.firebase:firebase-analytics – User behavior tracking.
* com.google.firebase:firebase-auth:23.1.0 – User authentication.
* com.google.firebase:firebase-database – Realtime database.
* com.google.android.gms:play-services-auth:21.3.0 – Google sign-in.

#### Networking & API
* com.squareup.retrofit2:retrofit:2.9.0 – REST API communication.
* com.squareup.retrofit2:converter-gson:2.9.0 – JSON serialization/deserialization.
* com.squareup.retrofit2:adapter-rxjava3:2.9.0 – RxJava support for Retrofit.
  
#### UI & Animation
* com.airbnb.android:lottie:6.6.2 – Lottie animations.
* com.github.bumptech.glide:glide:4.16.0 – Image loading and caching.
* com.google.android.material – Material design components.
  
#### Reactive Programming
* io.reactivex.rxjava3:rxandroid:3.0.2 – RxJava Android bindings.
* io.reactivex.rxjava3:rxjava:3.1.5 – Asynchronous programming.

### Features
✅ User Authentication – Sign in/up via Google and Firebase Auth.

✅ Real-time Database – Sync data with Firebase Realtime Database.

✅ Offline Storage – Room database for caching data locally.

✅ Smooth Animations – Lottie integration for better UI experience.

✅ Secure API Calls – Retrofit with RxJava for efficient data handling.

✅ Modern UI Design – Material Design components.

### Technology Used
#### Programming Language: 
* Java
  
#### Frameworks: 
* AndroidX
* Firebase
* Retrofit
* RxJava

#### Database: 
* Room
* Firebase Realtime Database

#### Design: 
* Material Design
* Lottie Animations

#### Authentication: 
* Google Sign-In
* Firebase Auth

### Architecture Used
* MVP Architecture
