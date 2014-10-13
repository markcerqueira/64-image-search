package mark.gimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import mark.gimagesearch.models.ImageSearchResponse;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fetchImages();
    }

    private void fetchImages() {
        Log.i(TAG, "fetchImages - called");

        GoogleImageSearchWrapper.fetchImages("herro world", new GoogleImageSearchWrapper.GoogleImageSearchCallbackInterface() {
            @Override
            public void imageSearchResponseReceived(ImageSearchResponse imageSearchResponse) {
                Log.i(TAG, "fetchImages - called back with " + imageSearchResponse.responseData.resultsList.size() + " images");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
