package org.suzuki.config;

public class ConfigUtil {

    public static int getIndexOf(int nodeId, Config config) {

        int i = 0;
        for(NodeConfig nodeConfig : config.getNodes()) {
            if(nodeId == nodeConfig.getId()) {
                return i;
            }
            ++i;
        }

        throw new RuntimeException("Could not find nod with id:" + nodeId + " in config:" + config);

    }

}
