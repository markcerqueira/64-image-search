package mark.gimagesearch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResult {
    @JsonProperty("GsearchResultClass") public String GsearchResultClass;
    @JsonProperty("width") public int width;
    @JsonProperty("height") public int height;
    @JsonProperty("imageId") public String imageId;
    @JsonProperty("tbWidth") public int tbWidth;
    @JsonProperty("tbHeight") public int tbHeight;
    @JsonProperty("unescapedUrl") public String unescapedUrl;
    @JsonProperty("url") public String url;
    @JsonProperty("visibleUrl") public String visibleUrl;
    @JsonProperty("title") public String title;
    @JsonProperty("titleNoFormatting") public String titleNoFormatting;
    @JsonProperty("originalContextUrl") public String originalContextUrl;
    @JsonProperty("content") public String content;
    @JsonProperty("contentNoFormatting") public String contentNoFormatting;
    @JsonProperty("tbUrl") public String tbUrl;

    private static final String TAG = ImageResult.class.getName();

    @SuppressWarnings("Jackson JSON parser requires a default constructor")
    public ImageResult() {

    }
}
