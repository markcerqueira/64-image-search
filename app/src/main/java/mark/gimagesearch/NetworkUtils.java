package mark.gimagesearch;

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
}
