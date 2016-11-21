import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import static java.awt.SystemColor.info;


/**
 * Created by Memfis on 2016-11-21.
 */
@SuppressWarnings("WeakerAccess")
public class WeatherService {

    private final String apiKey;

    public WeatherService(String apiKey){
        this.apiKey = apiKey;
    }

    /**
     * Returns weather info for given city
     * @param city
     * @return weatherInfo
     * @throws Exception
     */
    public WeatherInfo getWeatherFor(String city) throws  WeatherServiceException, Exception {
        JSONObject weatherJson = getData(city);
        int code = getErrorCode(weatherJson);
        if(code > 0){
            throw new WeatherServiceException(code);
        }

        return mapJSONObjectToWeatherInfo(weatherJson);
    }

    protected WeatherInfo mapJSONObjectToWeatherInfo(JSONObject weatherJson){
        WeatherInfo weatherInfo = new WeatherInfo();

        //getting proper object
        JSONObject info = weatherJson.getJSONObject("current");

        //extracting i know i could use some advanced mapper but it's just for learning purposes
        //will add more latter
        weatherInfo.setTemperatureC(info.getDouble("temp_c"));
        weatherInfo.setTemperatureF(info.getDouble("temp_f"));

        return weatherInfo;
    }

    protected JSONObject getData(String city) throws Exception{
        HttpResponse<JsonNode> weatherJsonResponse; //no need for async here
        try {
            String weatherApiUrlPattern = "http://api.apixu.com/v1/current.json";
            weatherJsonResponse = Unirest.get(weatherApiUrlPattern)
                    .queryString("key", this.apiKey)
                    .queryString("q", city)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return weatherJsonResponse.getBody().getObject();
    }

    /**
     * 0 if success 1+ if failed
     * @param weatherJson
     * @return int error code
     */
    protected int getErrorCode(JSONObject weatherJson) {
        if (weatherJson.has("error")) {
            return weatherJson.getJSONObject("error").getInt("code");
        }
        return 0;
    }
}
