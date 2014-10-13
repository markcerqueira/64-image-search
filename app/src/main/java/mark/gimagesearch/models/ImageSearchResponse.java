package mark.gimagesearch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageSearchResponse {
    @JsonProperty("responseData") public ResponseDataNode responseData;
    @JsonProperty("responseDetails") public JsonNode responseDetails;
    @JsonProperty("responseStatus") public int responseStatus;

    private static final String TAG = ImageSearchResponse.class.getName();

    @SuppressWarnings("Jackson JSON parser requires a default constructor")
    public ImageSearchResponse() {

    }
}
