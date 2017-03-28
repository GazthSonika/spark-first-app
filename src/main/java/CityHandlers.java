import orm.City;
import orm.CityDAO;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

import static spark.Spark.halt;


class CityHandlers {

    private final CityDAO cityDAO; //why spark has no autowire ;(

    public CityHandlers(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    Object postCityHandler(Request req, Response res){
        String cityName = req.queryParams("city");
        Double temperature = Double.parseDouble(req.queryParams("temperature"));
        //@TODO VALIDATION

        City city = new City(cityName, temperature); //could go to the service //btw is that sql safe thanks to hibernate?
        cityDAO.addCity(city);
        res.status(201);
        return null;
    }

    List<City> listCitiesHandler(Request req, Response res){
        List<City> cities = cityDAO.getAll();
        return cities;
    }

}
