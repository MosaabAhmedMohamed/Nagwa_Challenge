## Nagwa_Challenge

a simple mobile application to display
a list of videos and books to be downloaded and played or opened it.

## Challenge description
 - find fake responses for the list of videos and books in the attachments.
 - choose one or multiple files to download.
 - show the download percentage progress for each item downloading.
 - mark the item that was downloaded, the item chosen to download, and the item being downloaded in the UI.
 - download in order, and if the download fails, you can try three times and then show that the download failed.
 - open files downloaded from external applications that support this.
 
## What i have done
I started working on it by migrating gradle files to kotlin dsl then modeling the project to clean architecture ( core, app, data, domain, presentation) modules ,
applied clean architecture with MVVM design pattern used RX to handle heavy operations on the background thread,
Dagger 2 for dependency injection,Room for caching list of the tags,items, 
enabled proguard for obfuscating and securing the code base , 
unit testing the database and all the feature using (JUnit, Mockito, Robolectric),
used different branches to implement and refactor the features to avoid conflicts.


## Screenshot
<img src="https://github.com/MosaabAhmedMohamed/Nagwa_Challenge/blob/master/Nagwa_Challenge.png" width="200" height="400"></a>

## Download
[APK](https://github.com/MosaabAhmedMohamed/El-Menus_Challenge/blob/master/El-menus_challenge.apk)


## Specifications
- Caching the user for saving it's data.
- portrait and landscape.
- using Clean architecture.
- using MVVM
- using RxJava
- Using Usecases (part of clean architecture)
- Using View State
- Unit test.
- Partly include comments.
- Reactive code
- Kotlin DSL
- Enabling ProGuard

## Languages, libraries and tools used

 * [Kotlin](https://kotlinlang.org/)
 * [androidX libraries](https://developer.android.com/jetpack/androidx)
 * [Android LifeCycle](https://developer.android.com/topic/libraries/architecture)
 * [Glide](https://github.com/bumptech/glide)
 * [Room](https://developer.android.com/jetpack/androidx/releases/room)
 * [Retrofit2](https://github.com/square/retrofit)
 * [Dagger2](https://dagger.dev/)
 * [Moshi](https://github.com/square/moshi)
 * [RxJava](https://github.com/ReactiveX/RxJava)
 * [RxKotlin](https://github.com/ReactiveX/RxKotlin)
 * [RxAndroid](https://github.com/ReactiveX/RxAndroid)
 * [RoboElectric](http://robolectric.org/)
 
 
## Requirements
- min SDK 21

## Installation
HalanChallenge requires Android Studio version 3.6 or higher.

## Implementation

* In this project I'm using [Clean architecture with MVVM Pattern](https://developer.android.com/jetpack/docs/guide)
as an application architecture adopted with usage of UseCases with these design patterns in mind:-
- Repository Pattern
- Singleton
- Observables
- Adapters

* Using Dagger2 for dependency injection that will make testing easier and make our code 
cleaner and more readable and handy when creating dependecies.
* Using Retrofit library to handle the APIs stuff.
* Using Room for caching user data
* Separation of concerns : The most important principle used in this project to avoid many lifecycle-related problems.
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png"></a>
* Each component depends only on the component one level below it. For example, activities and fragments depend only on a view model. The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.
This design creates a consistent and pleasant user experience. Regardless of whether the user comes back to the app several minutes after they've last closed it or several days later, they instantly see a user's information that the app persists locally. If this data is stale, the app's repository module starts updating the data in the background.
* Using to best of managing ViewState with less complex tools , using Sealed Classes and LiveData we created a solid source that we can expose to view to show what the app can do to the user without worrying about the side effects
```
sealed class FilesViewState {
    object Loading : FilesViewState()
    object onEmptyState : FilesViewState()
    data class onSuccess(val result: List<ItemUiModel>) : FilesViewState()
    data class onError(val error: Throwable) : FilesViewState()

}
```
A View will a) emit its event to a ViewModel, and b) subscribes to this ViewModel in order to receive states needed to render its own UI.


Then ViewModel starts to delegate the event to it's suitable UseCase with thread handling in mind using RxJava (Logic Holders for each case)

```
  fun getFiles(isForceRefresh:Boolean = false) {
             getFilesUseCase.getFiles(isForceRefresh)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
                    .subscribe({
                        ....
                    }, {
                       ....
                    }
    }
```
As the UseCase process and get the required models form the repository it returns the result back to the ViewModel to start Exposing it as LiveData to our Lifecycle Owner (Activity)
```
    override val _viewState = MutableLiveData<Event<FilesViewState>>()

```
```
               .subscribe({
                    filesViewStateLDPrivate.value = FilesViewState.onSuccess(it.products.mapToUiModels())
                }
            }, {
                filesViewStateLDPrivate.value = FilesViewState.onError(it)
            })
```

then back to our Activity it will listen for any change in our LiveData ( ViewState Holder) and React to it.

```
 viewModel.viewState.observe(this, Observer {
            render(it)
        })
```

```
 private fun handleViewState(viewState: FilesViewState) {
        when (viewState) {
            FilesViewState.Loading -> loadingState()
            FilesViewState.onEmptyState -> emptyState()
            is FilesViewState.onError -> errorState(viewState.error)
            is FilesViewState.onSuccess -> onProductsLoaded(viewState.result)
        }

    }

```

## Immutability
Data immutability is embraced to help keeping the logic simple. Immutability means that we do not need to manage data being mutated in other methods, in other threads, etc; because we are sure the data cannot change. Data immutability is implemented with LiveData class.

## ViewModel LifeCycle
The ViewModel should outlive the View on configuration changes. For instance, on rotation, the Activity gets destroyed and recreated but your ViewModel should not be affected by this. If the ViewModel was to be recreated as well, all the ongoing tasks and cached latest ViewState would be lost.
We use the Architecture Components library to instantiate our ViewModel in order to easily have its lifecycle correctly managed.
