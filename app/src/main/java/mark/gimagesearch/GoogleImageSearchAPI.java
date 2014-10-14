package mark.gimagesearch;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
        NetworkUtils.runInThreadPool(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();

                try {
                    int page = (googleImageList == null ? 0 : googleImageList.getNextPageIndex()) * 4;

                    // TODO - error if pages is > 60

                    URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + URLEncoder.encode(searchTerm, "UTF-8") + "&start=" + URLEncoder.encode(page + "", "UTF-8"));

                    Log.i(TAG, "fetchImages - request url is: " + url.toString());

                    URLConnection connection = url.openConnection();
                    connection.addRequestProperty("Referer", "www.mark.gg");

                    String line;
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    Log.i(TAG, "fetchImages - response received: " + builder.toString());
                } catch (Exception e) {
                    Log.e(TAG, "fetchImages - exception thrown: " + e);

                    if (callbackListener != null) {
                        callbackListener.imageSearchResponseReceived(null);
                    }
                }

                ImageSearchResponse imageSearchResponse = JsonUtils.parseJson(builder.toString(), ImageSearchResponse.class);

                Log.i(TAG, "fetchImages - ImageSearchResponse object created");

                if (callbackListener != null) {
                    if(googleImageList == null) {
                        callbackListener.imageSearchResponseReceived(new GoogleImageList(imageSearchResponse));
                    } else {
                        GoogleImageList newGoogleImageList = googleImageList;
                        newGoogleImageList.appendImageUrls(imageSearchResponse);

                        callbackListener.imageSearchResponseReceived(newGoogleImageList);
                    }
                }
            }
        });
    }
}
