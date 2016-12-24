package org.suzuki.util;

import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiRequestBody;

public class MessageGenerator {

    public static SuzukiRequest generateRequest() {
        SuzukiRequestBody value = new SuzukiRequestBody();
        value.setRequestNumber(11);

        SuzukiRequest suzukiRequest = new SuzukiRequest();
        suzukiRequest.setSenderId(1);
        suzukiRequest.setType("request");
        suzukiRequest.setValue(value);

        return suzukiRequest;
    }

}
