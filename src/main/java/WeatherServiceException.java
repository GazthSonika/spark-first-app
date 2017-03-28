/**
 * Created by Memfis on 2016-11-21.
 */
public class WeatherServiceException extends Exception {

    private int code;

    public WeatherServiceException(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public String getMessage(){
        switch (this.code) { //@todo implement more codes
            case 1006:
                 return "No location found";
            case 2006:
            case 2007:
            case 2008:
                return "Problem with api key";
        }
        return "Unknown Error";
    }

}
