import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by Memfis on 2016-11-21.
 * thank you stack overflow ;)
 */
class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}