import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.*;

/**
 * Created by Memfis on 2016-11-20.
 */
public class Config {

    @SerializedName("apixu_key")
    @Expose
    private String apixuKey;

    public String getApixuKey() {
        return apixuKey;
    }


    private static Config instance = null;

    private Config(){}

    public static Config Instance() {
        if (instance == null) {
            Gson gson = new Gson();

            InputStream in = Config.class.getResourceAsStream("/config.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            instance = gson.fromJson(reader, Config.class);

        }
        return instance;
    }
}
