package org.suzuki.config;

import com.google.gson.Gson;
import org.suzuki.config.exception.ConfigParseException;

public class ConfigParser {

    private static Gson gson = new Gson();

    public static Config parse(String json) throws ConfigParseException {
        try {
            return gson.fromJson(json, Config.class);
        } catch(Exception e) {
            throw new ConfigParseException(e);
        }
    }


}
