package mark.gimagesearch;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.image_grid_list_item)
public class ImageGridListItem extends LinearLayout {
    private static final String TAG = ImageGridListItem.class.getName();

    @ViewById(R.id.imageview) protected ImageView mImageView;

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
}
