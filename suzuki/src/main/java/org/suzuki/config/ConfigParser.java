package org.suzuki.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.suzuki.config.exception.ConfigParseException;

public class ConfigParser {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Config parse(String json) throws ConfigParseException {
        try {
            return gson.fromJson(json, Config.class);
        } catch(Exception e) {
            throw new ConfigParseException(e);
        }
    }

//    public static String toJson(Config config) throws ConfigParseException {
//        try {
//            return gson.toJson(config);
//        } catch(Exception e) {
//            throw new ConfigParseException(e);
//        }
//    }


}
