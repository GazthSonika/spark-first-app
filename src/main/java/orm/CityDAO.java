package orm;

import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;


public class CityDAO {

    private SessionFactory sf;

    public CityDAO(SessionFactory sf) {
        this.sf = sf;
    }

    public void addCity(City city){
        sf.getCurrentSession().update(city);
    }

    public List<City> getAll(){
        //isnt smh like get /find all...
        List<City> cities = sf.openSession().createCriteria(City.class).list();
        return cities;
    }

}
