import orm.City;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import orm.CityDAO;
import spark.Spark;


import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static spark.Spark.*;

public class Main {


    private static void checkEnvVars(){
        //Check env vars lazy way
        List<String> requiredEnv = asList("APIXU_KEY", "DB_USER", "DB_PASS");
        requiredEnv.forEach(e -> {
            if(e == null){
                System.out.println(e + " env not present");
                System.exit(1);
            }
        });
    }

    private static SessionFactory getSessionFactory(){
        //HIBERNATE Bebe (using from code as i want to use ENV vars and not this ugly xml)
        Configuration configuration = new Configuration();
        //configuration.configure();
        //config
        configuration.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
        configuration.setProperty("hibernate.connection.password", System.getenv("DB_PASS"));
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/spark-test-app");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.current_session_context_class", "thread");
        configuration.setProperty(" hibernate.connection.pool_size", "10");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        //entities
        configuration.addAnnotatedClass(City.class);

        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }


    public static void main(String[] args){
        checkEnvVars();
        //CONFIG
        //envVars
        String key = System.getenv("APIXU_KEY");

        //aww how to log properly TODO
        System.out.println("apixu key: " + key); //12 factor app FTW
        //services would need some nice and fancy DI here

        //SERVICES (isn't that called in java world beans? need to check that out)
        //Are there any nice DI or all glory to hypnoSPRING (hope that spring is similar to symfony 2/3) :D
        WeatherService weatherService = new WeatherService(key);
        SessionFactory sessionFactory = getSessionFactory();

        //DAO
        CityDAO cityDAO = new CityDAO(sessionFactory);

        //HANDLERS
        WeatherHandlers weatherHandlers = new WeatherHandlers(weatherService);
        CityHandlers cityHandlers = new CityHandlers(cityDAO);
        /**
         * Does creating bigger controllers have sense?
         * if i want controllers and many endpoints I should go spring instead shouldn't I
         */
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace());
        //ROUTER (would be nice if can be done by annotations)
        get("/weather", (req, res) -> cityHandlers.listCitiesHandler(req, res));
        post("/weather", (req, res) -> cityHandlers.postCityHandler(req, res), JsonUtil::toJson);
        get("/weather/:city", (req, res) -> weatherHandlers.getWeatherHandler(req, res), JsonUtil::toJson);


    }
}