package org.suzuki.algorithm.utils;

import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.ElectBroadcast;
import org.suzuki.data.ElectBroadcastBody;

public class ElectBroadcastBuilder {

    private Config config;

    public ElectBroadcastBuilder() {
        this.config = ConfigHolder.getConfig();
    }

    public ElectBroadcast build() {
        ElectBroadcastBody value = new ElectBroadcastBody();
        value.setElectNodeId(config.getMyId());

        ElectBroadcast electBroadcast = new ElectBroadcast();
        electBroadcast.setSenderId(config.getMyId());   //TODO move to message constructor

        electBroadcast.setValue(value);

        return electBroadcast;
    }

}