package org.suzuki.algorithm;

import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RN {

    // <nodeId, number>
    private LinkedHashMap<Integer, Integer> RN;

    public RN() {
        // initialize map
        Config config = ConfigHolder.getConfig();
        RN = new LinkedHashMap<>((int) Math.ceil(config.getNodes().size() / 0.75));
        config.getNodes().forEach(
                node -> RN.put(node.getId(), 0)
        );
    }

    public int numberOf(int nodeId) {
        return RN.get(nodeId);
    }

    public void setNumber(int nodeId, int number) {
        RN.put(nodeId, number);
    }

    public Set<Map.Entry<Integer, Integer>> entrySet() {
        return RN.entrySet();
    }

    @Override
    public String toString() {
        return RN.toString();
    }
}
