package mark.gimagesearch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDataNode {
    @JsonProperty("results") public List<ImageResult> resultsList = new ArrayList<ImageResult>();
    @JsonProperty("cursor") public CursorDataNode cursorDataNode;

    private static final String TAG = ResponseDataNode.class.getName();

    @SuppressWarnings("Jackson JSON parser requires a default constructor")
    public ResponseDataNode() {

    }
}
