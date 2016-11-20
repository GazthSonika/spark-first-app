import DTO.Current;
import DTO.Location;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

import java.util.Map;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        //i know apixu has it's lib
        //TODO simple service manager
        //TODO weaterApi as service + interface
        //TODO some transfer and data objects
        String weatherApiUrlPattern = "http://api.apixu.com/v1/current.json";
        String key = env.get("APIXU_KEY");
        //aww how to log properly TODO
        System.out.println("apixu key: "+key);

        get("/weather/:city", (req, res) -> {
            String city = req.params("city");
            if (city == null) {
                halt(400, "No city given");
            }

            HttpResponse<JsonNode> weatherJsonResponse = Unirest.get(weatherApiUrlPattern)
                    .queryString("key", key)
                    .queryString("q", city)
                    .asJson(); //no need for async here

            JSONObject weatherJson = weatherJsonResponse.getBody().getObject();


            if (weatherJson.has("error")) {
                int code = weatherJson.getJSONObject("error").getInt("code");
                switch (code) {
                    case 1006:
                        halt(404, "No location found");
                        break;
                    case 2006:
                    case 2007:
                    case 2008:
                        halt(500, "Problem with api key");
                        break;
                }
            }

            //could to that in fly but that way i'd skip some learning

             Gson gson = new Gson();
             Current current = gson.fromJson(weatherJson.getJSONObject("current").toString(), Current.class);
             return "siema  -> "+current.toString();
            /**/

        });
    }
}