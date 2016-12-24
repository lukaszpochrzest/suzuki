package org.suzuki.util;

import org.suzuki.data.*;

import java.util.Arrays;

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

    public static SuzukiToken generateToken() {
        SuzukiTokenBodyElement suzukiTokenBodyElement = new SuzukiTokenBodyElement();
        suzukiTokenBodyElement.setNodeId(1);
        suzukiTokenBodyElement.setNumber(12);

        SuzukiTokenBody value = new SuzukiTokenBody();
        value.setLastRequests(Arrays.asList(suzukiTokenBodyElement));
        value.setQueue(new int[] {1,2} );

        SuzukiToken suzukiToken = new SuzukiToken();
        suzukiToken.setSenderId(1);
        suzukiToken.setType("token");
        suzukiToken.setValue(value);

        return suzukiToken;
    }

}
