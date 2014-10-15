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

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
    @ViewById(R.id.no_results_textview) protected TextView mNoResultsTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureImageLoader();

        setupAdapters();
    }

    private void configureImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(20)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)
                .build();

        ImageLoader.getInstance().init(config);
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
                if (convertView == null || !(convertView instanceof ImageGridListItem)) {
                    convertView = ImageGridListItem.newInstance(MainActivity.this);
                }

                final ImageGridListItem imageGridListItem = (ImageGridListItem) convertView;

                imageGridListItem.configure(mCurrentGoogleImageList.getImageUrlList().get(position));

                return convertView;
            }
        };
    }

    // the main workhouse of our application
    // if a GoogleImageList is provided, this method will append the results of the network call
    // to this list (up to 64 images)
    // once 64 images have been retrieved or some network error is thrown (via boolean flag passed
    // in), the method will refresh the list view
    private void fetchImages(final String searchQuery, final GoogleImageList googleImageList, final boolean previousNetworkCallSucceeded) {
        Log.i(TAG, "fetchImages - called with query: " + searchQuery);

        if(searchQuery == null) {
            Log.e(TAG, "fetchImages - search query is null");

            // TODO show Toast explaining error
        }

        // if googleImageList is null, we are starting a query, so show loading view
        if(googleImageList == null) {
            mLoadingImagesView.setVisibility(View.VISIBLE);

            // update text for loading (and potentially showing no results too)
            mLoadingImagesTextView.setText(String.format(getString(R.string.loading_images_of_s), searchQuery));
            mNoResultsTextView.setText(String.format(getString(R.string.no_image_results_of_s), searchQuery));

            mNoResultsView.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "fetchImages - passed googleImageList contains " + googleImageList.getImageUrlList().size() + " images");
        }

        // if our previous call failed (abort early) OR we've fetched the max number of images
        if (!previousNetworkCallSucceeded || (googleImageList != null && googleImageList.hasFetchedMaxImages())) {
            Log.i(TAG, "fetchImages - time to show the results!");

            mImageGridView.setAdapter(mImageAdapter);
            updateViewVisibility(googleImageList.getImageUrlList().size());

            return;
        } else if(googleImageList != null && googleImageList.getImageUrlList().size() == 0) {
            updateViewVisibility(0);
            return;
        }

        GoogleImageSearchAPI.fetchImages(searchQuery, googleImageList, new GoogleImageSearchAPI.GoogleImageSearchCallbackInterface() {
            @Override
            public void imageSearchResponseReceived(GoogleImageList googleImageList) {
                Log.i(TAG, "fetchImages - called back with " + googleImageList.getImageUrlList().size() + " images");

                // Log.i(TAG, "fetchImages - url list is: " + googleImageList.imageUrlListToString());

                updateWithNewGoogleImageList(googleImageList, googleImageList != null);
            }
        });
    }

    @UiThread
    protected void updateWithNewGoogleImageList(GoogleImageList googleImageList, boolean networkCallSucceeded) {
        if(googleImageList != null) {
            mCurrentGoogleImageList = googleImageList;
        }

        // pro-actively fetch the URLs for the rest of the images (up to 64)
        // if we failed (previous return result was null), we will not try to fetch anymore
        fetchImages(mCurrentGoogleImageList.getSearchTerm(), googleImageList, networkCallSucceeded);
    }

    @UiThread
    protected void updateViewVisibility(int itemCount) {
        mLoadingImagesView.setVisibility(View.GONE);

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit - " + query);

                searchView.clearFocus();

                fetchImages(query, null, true);

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
