package org.suzuki.algorithm.utils;

import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiRequestBody;

public class SuzukiRequestBuilder {

    private Config config;

    public SuzukiRequestBuilder() {
        this.config = ConfigHolder.getConfig();
    }

    public SuzukiRequest build(int requestNumber) {
        SuzukiRequestBody value = new SuzukiRequestBody();
        value.setRequestNumber(requestNumber);

        SuzukiRequest suzukiRequest = new SuzukiRequest();
        suzukiRequest.setSenderId(config.getMyId());    //TODO move to message constructor

        suzukiRequest.setValue(value);

        return suzukiRequest;
    }

}