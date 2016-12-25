package org.suzuki.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class Config {

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private int myId;

    @Getter
    @Setter
    private List<NodeConfig> nodes;

    public int getIndexOf(int nodeId) {

        int i = 0;
        for(NodeConfig nodeConfig : getNodes()) {
            if(nodeId == nodeConfig.getId()) {
                return i;
            }
            ++i;
        }

        throw new RuntimeException("Could not find nod with id:" + nodeId + " in config:" + this);
    }

    public int getNodeIdFor(int nodeIndex) {
        return getNodes().get(nodeIndex).getId();
    }

}
