package mark.gimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import mark.gimagesearch.models.ImageSearchResponse;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
    }

    private void fetchImages(String searchQuery) {
        Log.i(TAG, "fetchImages - called with query: " + searchQuery);

        if(searchQuery == null) {
            Log.e(TAG, "fetchImages - search query is null");

            // TODO show error toast
        }

        GoogleImageSearchWrapper.fetchImages(searchQuery, new GoogleImageSearchWrapper.GoogleImageSearchCallbackInterface() {
            @Override
            public void imageSearchResponseReceived(ImageSearchResponse imageSearchResponse) {
                Log.i(TAG, "fetchImages - called back with " + imageSearchResponse.responseData.resultsList.size() + " images");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // add the magnifying glass (search widget)
        MenuItem searchItem = menu.findItem(R.id.options_menu_main_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        configureSearchElements(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    // configures all the listeners related to the search functionality
    private void configureSearchElements(final SearchView searchView) {
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "setOnSearchClickListener - begin");
            }
        });

        // configure the clear search text field action
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i(TAG, "onClose");

                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit - " + query);

                searchView.clearFocus();

                fetchImages(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange - " + newText);

                return true;
            }
        });
    }
}
