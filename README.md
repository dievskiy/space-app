# Application

This is my application written in Kotlin for Android.

In this project, I've ended up using MVVM, Livedata, Retrofit for network communication and Glide for displaying images. I've created my own class for dependency injection because using DI frameworks would be overkill for this simple project.

When the user triggers retrieval, articles are fetched from the API endpoint and pushed into the RecyclerView.
For each article, title, image and date are displayed.

![](https://imgur.com/0hWZrH0.png)

To run tests, use:

```
./gradlew test
```