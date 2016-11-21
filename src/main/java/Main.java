import spark.Request;
import spark.Response;


import static spark.Spark.*;

public class Main {

    public static void main(String[] args){
        //CONFIG
        String key = System.getenv("APIXU_KEY");
        if (key == null) {
            System.out.println("APIXU_KEY env not present");
            System.exit(1);
        }
        //aww how to log properly TODO
        System.out.println("apixu key: " + key);
        //services would need some nice and fancy DI here

        //SERVICES (isn't that called in java world beans? need to check that out)
        WeatherService weatherService = new WeatherService(key);

        /**
         * Does creating bigger controllers have sense?
         * if i want controllers and many endpoints I should go spring instead shouldn't I
         */
        //ROUTER
        get("/weather/:city", (req, res) 
                -> getWeatherHandler(req, res, weatherService), JsonUtil::toJson);
        
        get("/TODO_SOME_PAGE_TO_CONSUME_THE_API", (req, res) -> "I'm so lazyyy ;)");
    }

    //HANDLERS
    private static WeatherInfo getWeatherHandler(Request req, Response res, WeatherService weatherService){
        String city = req.params("city");
        if (city == null) {
            halt(400, "No city given");
        }
        try {
            return weatherService.getWeatherFor(city);
        }
        catch(WeatherServiceException e){
            System.out.println("Weather service exception");
            halt(400, e.getMessage());
            return null;
        }
        catch (Exception e){
            halt(500, e.getMessage());
            return null;
        }
    }
}