package mark.gimagesearch;

import android.util.Log;

import java.util.ArrayList;

import mark.gimagesearch.models.ImageResult;
import mark.gimagesearch.models.ImageSearchResponse;

public class GoogleImageList {
    private static final String TAG = GoogleImageList.class.getName();

    private ArrayList<String> mImageUrlList = new ArrayList<String>();
    private String mSearchTerm;
    private int pagesFetched;

    public GoogleImageList(String searchTerm, ImageSearchResponse imageSearchResponse) {
        mSearchTerm = searchTerm;
        appendImageUrls(imageSearchResponse);
        pagesFetched = 0;
    }

    public ArrayList<String> getImageUrlList() {
        return mImageUrlList;
    }

    public String getSearchTerm() {
        return mSearchTerm;
    }

    public String imageUrlListToString() {
        StringBuilder sb = new StringBuilder();

        for(String imageUrl : mImageUrlList) {
            sb.append(imageUrl);
            sb.append(",\n");
        }

        sb.setLength(sb.length() - 2);

        return sb.toString();
    }

    public boolean canFetchMoreResults() {
        return pagesFetched < 16;
    }

    public boolean hasFetchedMaxImages() {
        return mImageUrlList.size() == 64;
    }

    public int getNextPageIndex() {
        return pagesFetched + 1;
    }

    public void appendImageUrls(ImageSearchResponse imageSearchResponse) {
        if(imageSearchResponse == null) {
            Log.e(TAG, "GoogleImageList - null ImageSearchResponse passed to constructor");
            return;
        }

        for(ImageResult imageResult : imageSearchResponse.responseData.resultsList) {
            mImageUrlList.add(imageResult.tbUrl);
        }

        pagesFetched++;
    }
}
