package mark.gimagesearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

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
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Add SearchWidget.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.options_menu_main_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /* int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } */
        return super.onOptionsItemSelected(item);
    }
}
