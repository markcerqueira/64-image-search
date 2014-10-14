package mark.gimagesearch.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getName();

    private ScheduledThreadPoolExecutor mThreadPoolExecutor = null;

    private static NetworkUtils sInstance;

    public static synchronized NetworkUtils getInstance() {
        if(sInstance == null) {
            sInstance = new NetworkUtils();
        }

        return sInstance;
    }

    private NetworkUtils() {
        mThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
    }

    public static Future<?> runInThreadPool(Runnable runnable) {
        return getInstance().mThreadPoolExecutor.submit(runnable);
    }

    public static String callUrl(URL url) {
        if(url == null) {
            Log.e(TAG, "callUrl - null url; returning null string");
            return null;
        }

        StringBuilder builder = new StringBuilder();

        try {
            URLConnection connection = url.openConnection();

            // TODO do we need this referer property?
            connection.addRequestProperty("Referer", "www.mark.gg");

            String line;
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch(IOException exception) {
            Log.e(TAG, "callUrl - exception thrown when calling url, " + url.toString() + ": " + exception);
            return null;
        }

        return builder.toString();
    }
}
