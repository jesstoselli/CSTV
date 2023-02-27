# CSTV 🎮
 
CSTV is a Kotlin Android application built to list CS Go matches, in order to demonstrate core Android Development skills.

It makes use of the [PandaScore Api](https://pandascore.co/).
  
This app demonstrates the following views and techniques using libs:  
  
* [Retrofit](https://square.github.io/retrofit/) for making API calls to an HTTP web service.  
* [Ok HTTP](https://square.github.io/okhttp/) for handing network calls.
* [Gson](https://github.com/google/gson)  which handles the deserialization of the returned JSON to Kotlin data objects. 
* [Glide](https://bumptech.github.io/glide/)  which handles proper image presentation. 
* [Koin](https://insert-koin.io/docs/reference/koin-android/start) for dependency injection.
* [JUnit4](https://junit.org/junit4/) for unit tests.
* [Mockk](https://mockk.io/ANDROID.html) for mocking objetcs in unit testing.

It leverages the following components from the Jetpack library:  
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters  
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the SafeArgs plugin for parameter passing between fragments  
* [Material Design](https://m3.material.io/)

## Screenshots

Click to expand.<br>
<img src="https://github.com/jesstoselli/cstv-challenge/blob/master/screenshots/splashscreen.png" width="25%"/>
<img src="https://github.com/jesstoselli/cstv-challenge/blob/master/screenshots/NowVSNext.png" width="25%"/>
<img src="https://github.com/jesstoselli/cstv-challenge/blob/master/screenshots/MatchDetails.png" width="25%"/>
  
  
## Getting Started  
  
To get started with this project, pull the repository and import the project into Android Studio. 

You must then register in the API website and get an access token, which you'll then add to the object ServiceHelpers.

From there, deploy the project to an emulator or device.
  
## Report Issues  
Notice any issues with a repository? Please file a github issue in the repository.
