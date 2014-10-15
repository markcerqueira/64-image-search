64 Image Search
=============

About
------

* This is a basic Android application that allows for searching for images using the Google Image Search API.
* Results are displayed in a 3-column scroll view.
* The minimum OS version required to run the application is Ice Cream Sandwich (4.0).
* The Google Image Search API can only return up to 64 images (hence the name of the app). This limit is communicated to the user when they first launch the app.
* Google Image Search API returns both full-size image URLs and thumbnail URLs. This app uses thumbnail URLs for displaying images (see comment in Notes section for reasoning).

File Overview
------
This section briefly describes the role of packages and files in the mark.gimagesearch package (app/src/main/java/mark/gimagesearch):

* models folder - contains various "model" classes that are used to auto-parse the response from the Google Image API using the Jackson JSON annotations
* utils folder - contains a JSON utilities class for auto-parsing strings/JsonNodes into objects and NetworkUtils, a network functionality helper class
* GoogleImageSearchAPI - makes the network call to Google Image Search, parsing the response using the classes in the models folder, and then pushes that object into the GoogleImageList which provides a simplified data-set for the caller of this class
* GoogleImageList - the main network response class is ImageSearchResponse (in models folder), but this class contains only the information we use in the app with some additional helper methods
* SquareImageView - a borrowed class that allows us to display images in a square, which makes the grid view look a lot nicer
* ImageGridListItem - the items that populate the grid view; uses the SquareImageView to display images in a uniform manner
* MainActivity - the main (and only) activity of our app; interfaces with the GoogleImageSearchAPI to make queries, which returns a GoogleImageList, and has a grid view that contains ImageGridListItems

Notes
------

* If you're developing this app and Android Studio is complaining about the run configuration being bad because the default activity cannot be found, try forcing Gradle to sync.
* The results returned from Google sometimes contain valid thumbnail URLs, but the actual full-size image URL points to a resource that cannot be accessed.

Improvements
------

Here are some things that could be improved with further development:

* The app currently fetches all 64 images before displaying any.
We could start showing images as they are fetched, but this means we also need to selectively refresh grid view elements.
Or we could also fetch a screen's worth of images and display those, fetching the rest (off screen images) following that. This would require determining how many images can be displayed on the screen using screen metrics.
* Better error handling for network calls. Currently, if a network call fails we abort trying to fetch any more results.
* For screens with higher density, use the actual resource URL instead of the thumbnail. If the resource URL cannot be fetched (see note in Notes above), use the thumbnail URL.
* Fetching 64 images requires making 16 (sad) network calls; cache results from network requests to improve performance for repeated searches within a reasonable time period (i.e. on the order of an hour?).
* Clicking on an image in the grid view should expand the image to take up more of the screen.
* Screen rotation is not supported. Use @InstanceState to save relevant data about current activity state and rebuild activity following re-creation after rotation.
* If a search is underway, if you are quick, you can start another search, which would likely cause some issues.
* No support for off-line. Improve messaging to users trying to use this app when not connected to a network.
* Address the TODOs (e.g. updating URL query params, error-checking on page count in fetchImages call in GoogleImageSearchAPI, show error toast if search query is null, check if we need "referrer" property on URL connection).
* Get hired to work at Google on the Images API and make it better. :)

External Libraries and Sources
------
External libraries were used in this project. They are: 

* [Google Image API Developer Guide][5] - describes the core API used in this app
* [Jackson JSON Processor][1] - used to bind network response strings into Java objects
* [Android Annotations][2] - makes Android development much better
* [Universal Image Loader][3] - library for loading, caching, and displaying images
* [SquareImageView][4] - used to display images more cleanly (i.e. all squares, more snug) in the GridView

[1]: http://wiki.fasterxml.com/JacksonHome
[2]: http://androidannotations.org/
[3]: https://github.com/nostra13/Android-Universal-Image-Loader
[4]: http://www.rogcg.com/blog/2013/11/01/gridview-with-auto-resized-images-on-android
[5]: https://developers.google.com/image-search/v1/devguide
