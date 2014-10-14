package mark.gimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.main_activity)
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    private BaseAdapter mImageAdapter;
    private GoogleImageList mCurrentGoogleImageList;

    @ViewById(R.id.introduction_view) protected View mIntroductionView;

    @ViewById(R.id.image_gridview_container_view) protected View mGridViewContainerView;
    @ViewById(R.id.image_gridview) protected GridView mImageGridView;

    @ViewById(R.id.loading_view) protected View mLoadingImagesView;
    @ViewById(R.id.loading_images_textview) protected TextView mLoadingImagesTextView;

    @ViewById(R.id.no_results_view) protected View mNoResultsView;
    @ViewById(R.id.no_results_textview) protected View mNoResultsTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupAdapters();
    }

    private void setupAdapters() {
        mImageAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mCurrentGoogleImageList.getImageUrlList().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.i(TAG, "getView - called for position: " + position);

                if (convertView == null || !(convertView instanceof ImageGridListItem)) {
                    convertView = ImageGridListItem.newInstance(MainActivity.this);
                }

                final ImageGridListItem imageGridListItem = (ImageGridListItem) convertView;

                return convertView;
            }
        };
    }

    private void fetchImages(final String searchQuery) {
        Log.i(TAG, "fetchImages - called with query: " + searchQuery);

        if(searchQuery == null) {
            Log.e(TAG, "fetchImages - search query is null");

            // TODO show error toast
        }

        GoogleImageSearchAPI.fetchImages(searchQuery, new GoogleImageSearchAPI.GoogleImageSearchCallbackInterface() {
            @Override
            public void imageSearchResponseReceived(GoogleImageList googleImageList) {
                Log.i(TAG, "fetchImages - called back with " + googleImageList.getImageUrlList().size() + " images");

                Log.i(TAG, "fetchImages - url list is: " + googleImageList.imageUrlListToString());

                /* GoogleImageSearchAPI.fetchImages(searchQuery, googleImageList, new GoogleImageSearchAPI.GoogleImageSearchCallbackInterface() {
                    @Override
                    public void imageSearchResponseReceived(GoogleImageList googleImageList) {
                        Log.i(TAG, "fetchImages - called back with " + googleImageList.getImageUrlList().size() + " images");

                        Log.i(TAG, "fetchImages - url list is: " + googleImageList.imageUrlListToString());
                    }
                }); */

                mCurrentGoogleImageList = googleImageList;

                mImageGridView.setAdapter(mImageAdapter);

                updateViewVisibility(googleImageList.getImageUrlList().size());
            }
        });
    }

    @UiThread
    protected void updateViewVisibility(int itemCount) {
        mIntroductionView.setVisibility(View.GONE);

        mGridViewContainerView.setVisibility(itemCount == 0 ? View.GONE : View.VISIBLE);
        mNoResultsView.setVisibility(itemCount == 0 ? View.VISIBLE : View.GONE);
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
