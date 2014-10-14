package mark.gimagesearch.utils;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getName();

    private static ObjectMapper sObjectMapper = new ObjectMapper();

    public static ObjectMapper defaultMapper() {
        return sObjectMapper;
    }

    public static <T> T parseJson(JsonNode jsonNode, Class<T> cls) {
        try {
            return JsonUtils.defaultMapper().treeToValue(jsonNode, cls);
        } catch (IOException e) {
            Log.e(TAG, "parseJson - failed to parse JSON entity " + cls.getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseJson(String jsonString, Class<T> cls) {
        try {
            JsonNode jsonNode = JsonUtils.defaultMapper().readValue(jsonString, JsonNode.class);

            if (jsonNode != null) {
                return JsonUtils.parseJson(jsonNode, cls);
            }
        } catch (IOException e) {
            Log.e(TAG, "parseJson - IOException thrown parsing JSON");
        }

        return null;
    }
}
