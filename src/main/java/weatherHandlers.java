import spark.Request;
import spark.Response;

import static spark.Spark.halt;


class WeatherHandlers {

    private final WeatherService weatherService;

    WeatherHandlers(WeatherService _ws) {
        weatherService = _ws;
    }

    //HANDLERS
    //Todo do smh with halt
    WeatherInfo getWeatherHandler(Request req, Response res){
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
