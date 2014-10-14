package mark.gimagesearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.image_grid_list_item)
public class ImageGridListItem extends LinearLayout {
    private static final String TAG = ImageGridListItem.class.getName();

    @ViewById(R.id.imageview) protected ImageView mImageView;
    @ViewById(R.id.loading_progress_spinner) protected ProgressBar mLoadingSpinner;

    public ImageGridListItem(Context context) {
        super(context);
    }

    public ImageGridListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageGridListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static ImageGridListItem newInstance(Context context) {
        ImageGridListItem instance = ImageGridListItem_.build(context);
        return instance;
    }

    public void configure(String imageUrl) {
        mImageView.setImageResource(android.R.color.transparent);

        ImageLoader.getInstance().displayImage(imageUrl, mImageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mLoadingSpinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mLoadingSpinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mLoadingSpinner.setVisibility(View.GONE);
            }
        });
    }
}
