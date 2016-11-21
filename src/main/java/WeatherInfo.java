import com.google.gson.annotations.Expose;

/**
 * Created by Memfis on 2016-11-21.
 */
public class WeatherInfo {

    @Expose
    private double temperatureC;
    @Expose
    private double temperatureF;


    public double getTemperatureC() {

        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public double getTemperatureF() {
        return temperatureF;
    }

    public void setTemperatureF(double temperatureF) {
        this.temperatureF = temperatureF;
    }
}

/**
 *     @SerializedName("last_updated_epoch")
 @Expose
 private Integer lastUpdatedEpoch;
 @SerializedName("last_updated")
 @Expose
 private String lastUpdated;
 @SerializedName("temp_c")
 @Expose
 private Double tempC;
 @SerializedName("temp_f")
 @Expose
 private Double tempF;
 @SerializedName("is_day")
 @Expose
 private Integer isDay;
 @SerializedName("condition")
 @Expose
 private Condition condition;
 @SerializedName("wind_mph")
 @Expose
 private Double windMph;
 @SerializedName("wind_kph")
 @Expose
 private Double windKph;
 @SerializedName("wind_degree")
 @Expose
 private Integer windDegree;
 @SerializedName("wind_dir")
 @Expose
 private String windDir;
 @SerializedName("pressure_mb")
 @Expose
 private Double pressureMb;
 @SerializedName("pressure_in")
 @Expose
 private Double pressureIn;
 @SerializedName("precip_mm")
 @Expose
 private Double precipMm;
 @SerializedName("precip_in")
 @Expose
 private Double precipIn;
 @SerializedName("humidity")
 @Expose
 private Integer humidity;
 @SerializedName("cloud")
 @Expose
 private Integer cloud;
 @SerializedName("feelslike_c")
 @Expose
 private Double feelslikeC;
 @SerializedName("feelslike_f")
 @Expose
 private Double feelslikeF;
 */