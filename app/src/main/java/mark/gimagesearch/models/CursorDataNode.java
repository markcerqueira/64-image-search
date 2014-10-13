package mark.gimagesearch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CursorDataNode {
    @JsonProperty("resultCount") public String resultCount;
    @JsonProperty("estimatedResultCount") public String estimatedResultCount;
    @JsonProperty("currentPageIndex") public long currentPageIndex;
    @JsonProperty("searchResultTime") public float searchResultTime;
    @JsonProperty("pages") public List<PageNode> pages = new ArrayList<PageNode>();

    private static final String TAG = CursorDataNode.class.getName();

    @SuppressWarnings("Jackson JSON parser requires a default constructor")
    public CursorDataNode() {

    }
}

