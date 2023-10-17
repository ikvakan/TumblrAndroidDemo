# TumblrAndroidDemo

A sample app that utilizes [Clean Architecture](https://developer.android.com/topic/architecture) pattern built entirely in [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjwvrOpBhBdEiwAR58-3Efmtt7RCA2tWHh_lpqTgklKWkiBqYpR8abscjcsILgj3VBXKlOehhoCqBgQAvD_BwE&gclsrc=aw.ds). The app displays Marvel posts from Tumblr Api and saves them in the local database.

## Setup
* Requires [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) 
  
`Make sure to check the build.gradle files for more details on library versions.`

`Important note: to use the services you need to add your Tumblr API KEY to project local.properties file: api_key="<your_api_key>"`

## Architecture
* MVVM (Model-View-ViewModel)
* [Clean Architecture](https://developer.android.com/topic/architecture#recommended-app-arch)
* [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
* [Coroutines](https://developer.android.com/kotlin/coroutines)

## Dependency management
* [Koin](https://github.com/InsertKoinIO/koin) - Dependency injection

## Network
* [Square/Retrofit](https://github.com/square/retrofit) - HTTP RESTful connections
* [OkHttp 3](https://square.github.io/okhttp/3.x/okhttp/)
* [Square/Moshi](https://github.com/square/moshi) - JSON adapter

## Persistence
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Room persistence library
  
## Other
### Design
* [Material 3](https://m3.material.io/develop/android/jetpack-compose)
### Image loading
* [Glide](https://github.com/bumptech/glide) - 
An image loading and caching library for Android focused on smooth scrolling
### Logging
* [Timber](https://github.com/JakeWharton/timber)
### Code quality
* [Ktlint](https://ktlint.github.io/) - An anti-bikeshedding Kotlin linter with built-in formatter
* [Detekt](https://github.com/arturbosch/detekt) - Static code analysis for Kotlin



## Disclaimer:
```PERSONAL PROJECT - NOT INTENDED FOR COMMERCIAL USE```

