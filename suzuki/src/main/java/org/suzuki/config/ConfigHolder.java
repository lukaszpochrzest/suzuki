package org.suzuki.config;

public class ConfigHolder {

    private static Config config;

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        ConfigHolder.config = config;
    }
}
