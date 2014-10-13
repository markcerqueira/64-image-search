package mark.gimagesearch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageNode {
    @JsonProperty("start") public String start;
    @JsonProperty("label") public int label;

    private static final String TAG = PageNode.class.getName();

    @SuppressWarnings("Jackson JSON parser requires a default constructor")
    public PageNode() {

    }
}