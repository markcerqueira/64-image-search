package mark.gimagesearch;

import android.util.Log;

import java.util.ArrayList;

import mark.gimagesearch.models.ImageResult;
import mark.gimagesearch.models.ImageSearchResponse;

public class GoogleImageList {
    private static final String TAG = GoogleImageList.class.getName();

    private ArrayList<String> mImageUrlList = new ArrayList<String>();

    public GoogleImageList(ImageSearchResponse imageSearchResponse) {
        if(imageSearchResponse == null) {
            Log.e(TAG, "GoogleImageList - null ImageSearchResponse passed to constructor");
            return;
        }

        for(ImageResult imageResult : imageSearchResponse.responseData.resultsList) {
            mImageUrlList.add(imageResult.url);
        }
    }

    public ArrayList<String> getImageUrlList() {
        return mImageUrlList;
    }

    public String imageUrlListToString() {
        StringBuilder sb = new StringBuilder();

        for(String imageUrl : mImageUrlList) {
            sb.append(imageUrl);
            sb.append("; ");
        }

        sb.setLength(sb.length() - 2);

        return sb.toString();
    }
}
