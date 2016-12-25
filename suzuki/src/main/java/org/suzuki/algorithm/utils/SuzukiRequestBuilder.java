package org.suzuki.algorithm.utils;

import org.suzuki.config.Config;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiRequestBody;

public class SuzukiRequestBuilder {

    private Config config;


    public SuzukiRequestBuilder(Config config) {
        this.config = config;
    }

    public SuzukiRequest build(int requestNumber) {
        SuzukiRequestBody value = new SuzukiRequestBody();
        value.setRequestNumber(requestNumber);

        SuzukiRequest suzukiRequest = new SuzukiRequest();
        suzukiRequest.setSenderId(config.getMyId());

        suzukiRequest.setValue(value);

        return suzukiRequest;
    }

}