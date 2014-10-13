package mark.gimagesearch;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import mark.gimagesearch.models.ImageSearchResponse;

public class GoogleImageSearchWrapper {
    private static final String TAG = GoogleImageSearchWrapper.class.getName();

    public interface GoogleImageSearchCallbackInterface {
        void imageSearchResponseReceived(ImageSearchResponse imageSearchResponse);
    }

    public static void fetchImages(final String searchTerm, final GoogleImageSearchCallbackInterface callbackListener) {
        NetworkUtils.runInThreadPool(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();

                try {
                    URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + URLEncoder.encode(searchTerm, "UTF-8"));
                    URLConnection connection = url.openConnection();
                    connection.addRequestProperty("Referer", "www.mark.gg");

                    String line;
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    Log.i(TAG, "fetchImages - response received: " + builder.toString());
                } catch(Exception e) {
                    Log.e(TAG, "fetchImages - exception thrown: " + e);

                    if(callbackListener != null) {
                        callbackListener.imageSearchResponseReceived(null);
                    }
                }

                ImageSearchResponse imageSearchResponse = JsonUtils.parseJson(builder.toString(), ImageSearchResponse.class);

                Log.i(TAG, "fetchImages - ImageSearchResponse object created");

                if(callbackListener != null) {
                    callbackListener.imageSearchResponseReceived(imageSearchResponse);
                }
            }
        });
    }
}
