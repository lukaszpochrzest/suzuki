package org.suzuki.config;

// TODO move these functions to class
public class ConfigUtils {

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

    public static int getNodeIdFor(int nodeIndex, Config config) {
        return config.getNodes().get(nodeIndex).getId();
    }

}
