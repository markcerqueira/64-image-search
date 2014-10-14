64 Image Search
=============

About
------

* This is a basic Android application that allows for searching for images using the Google Image Search API.
* Results are displayed in a 3-column scroll view.
* The minimum OS version required to run the application is Ice Cream Sandwich (4.0).
* The Google Image Search API can only return up to 64 images (hence the name of the app). This limit is communicated to the user when they first launch the app.
* Google Image Search API returns both full-size image URLs and thumbnail URLs. This app uses thumbnail URLs for displaying images (see comment in Notes section for reasoning).

Notes
------

* If you're developing this app and Android Studio is complaining about the run configuration being bad because the default activity cannot be found, try forcing Gradle to sync.
* The results returned from Google sometimes contain valid thumbnail URLs, but the actual full-size image URL points to a resource that cannot be accessed.


External Libraries and Sources
------
External libraries were used in this project. They are: 

* [Jackson JSON Processor][1] - used to bind network response strings into Java objects
* [Android Annotations][2] - makes Android development much better
* [Universal Image Loader][3] - library for loading, caching, and displaying images
* [SquareImageView][4] - used to display images more cleanly (i.e. all squares, more snug) in the GridView

[1]: http://wiki.fasterxml.com/JacksonHome
[2]: http://androidannotations.org/
[3]: https://github.com/nostra13/Android-Universal-Image-Loader
[4]: http://www.rogcg.com/blog/2013/11/01/gridview-with-auto-resized-images-on-android
