package mark.gimagesearch;

import android.util.Log;

import java.net.URL;
import java.net.URLEncoder;

import mark.gimagesearch.models.ImageSearchResponse;
import mark.gimagesearch.utils.JsonUtils;
import mark.gimagesearch.utils.NetworkUtils;

public class GoogleImageSearchAPI {
    private static final String TAG = GoogleImageSearchAPI.class.getName();

    public interface GoogleImageSearchCallbackInterface {
        void imageSearchResponseReceived(GoogleImageList googleImageList);
    }

    public static void fetchImages(final String searchTerm, final GoogleImageSearchCallbackInterface callbackListener) {
        fetchImages(searchTerm, null, callbackListener);
    }

    public static void fetchImages(final String searchTerm, final GoogleImageList googleImageList, final GoogleImageSearchCallbackInterface callbackListener) {
        // we REQUIRE that callbackListener be non-null
        if (callbackListener == null) {
            Log.e(TAG, "fetchImages - callbackListener CANNOT be null");
            return;
        }

        NetworkUtils.runInThreadPool(new Runnable() {
            @Override
            public void run() {
                int page = (googleImageList == null ? 0 : googleImageList.getNextPageIndex()) * 4;

                // TODO - error out if pages is > 60

                URL url = null;
                try {
                    // TODO - google says we should add IP address so they can verify we're not abusing the API
                    url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + URLEncoder.encode(searchTerm, "UTF-8") + "&start=" + URLEncoder.encode(page + "", "UTF-8"));
                } catch (Exception e) {
                    Log.e(TAG, "fetchImages - exception thrown when constructing url: " + e);
                    callbackListener.imageSearchResponseReceived(null);
                }

                Log.i(TAG, "fetchImages - request url is: " + url.toString());

                // make the network call
                String networkResponse = NetworkUtils.callUrl(url);

                // parse network response into an ImageSearchResponse object
                ImageSearchResponse imageSearchResponse = JsonUtils.parseJson(networkResponse, ImageSearchResponse.class);

                // if this is a "new" request (first request for an image) create a new GoogleImageList object
                if (googleImageList == null) {
                    Log.i(TAG, "fetchImages - new GoogleImageList object created");

                    callbackListener.imageSearchResponseReceived(new GoogleImageList(searchTerm, imageSearchResponse));
                } else {
                    Log.i(TAG, "fetchImages - appending result to existing GoogleImageList");

                    // otherwise, append the current result to the existing GoogleImageList and return that
                    GoogleImageList newGoogleImageList = googleImageList;
                    newGoogleImageList.appendImageUrls(imageSearchResponse);

                    callbackListener.imageSearchResponseReceived(newGoogleImageList);
                }
            }
        });
    }
}
