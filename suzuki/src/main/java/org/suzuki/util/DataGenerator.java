package org.suzuki.util;

import org.suzuki.config.Config;
import org.suzuki.config.NodeConfig;
import org.suzuki.data.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataGenerator {

    public static SuzukiRequest generateRequest() {
        SuzukiRequestBody value = new SuzukiRequestBody();
        value.setRequestNumber(11);

        SuzukiRequest suzukiRequest = new SuzukiRequest();
        suzukiRequest.setSenderId(1);
        suzukiRequest.setValue(value);

        return suzukiRequest;
    }

    public static SuzukiToken generateToken() {
        SuzukiTokenBodyElement suzukiTokenBodyElement = new SuzukiTokenBodyElement();
        suzukiTokenBodyElement.setNodeId(1);
        suzukiTokenBodyElement.setNumber(12);

        SuzukiTokenBody value = new SuzukiTokenBody();
        value.setLastRequests(Arrays.asList(suzukiTokenBodyElement));
        value.setQueue(Arrays.asList(1, 2));

        SuzukiToken suzukiToken = new SuzukiToken();
        suzukiToken.setSenderId(1);
        suzukiToken.setValue(value);

        return suzukiToken;
    }

    // TODO election
    public static SuzukiToken generateInitialToken(Config config) {

        List<SuzukiTokenBodyElement> elements = new ArrayList<>(config.getNodes().size());
        for(NodeConfig nodeConfig : config.getNodes()) {

            SuzukiTokenBodyElement suzukiTokenBodyElement = new SuzukiTokenBodyElement();
            suzukiTokenBodyElement.setNodeId(nodeConfig.getId());
            suzukiTokenBodyElement.setNumber(0);

            elements.add(suzukiTokenBodyElement);
        }

        SuzukiTokenBody value = new SuzukiTokenBody();
        value.setLastRequests(elements);
        value.setQueue(new ArrayList<>(0));

        SuzukiToken suzukiToken = new SuzukiToken();
        suzukiToken.setSenderId(config.getMyId());
        suzukiToken.setValue(value);

        return suzukiToken;
    }

//    public static Config generateConfig() {
//        NodeConfig nodeConfig1 = new NodeConfig();
//        nodeConfig1.setId(1);
//        nodeConfig1.setHost("localhost");
//        nodeConfig1.setPort(2345);
//
//        NodeConfig nodeConfig2 = new NodeConfig();
//        nodeConfig2.setId(2);
//        nodeConfig2.setHost("localhost");
//        nodeConfig2.setPort(2346);
//
//        Config config = new Config();
//        config.setMyId(1);
//        config.setPort(2345);
//        config.setNodes(Arrays.asList(nodeConfig1, nodeConfig2));
//
//        return config;
//    }


}
