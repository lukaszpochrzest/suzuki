package org.suzuki;

import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;

public class MessageHandlerImpl implements MessageHandler {

    @Override
    public void handle(SuzukiRequest suzukiRequest) {
        System.out.println("Handling suzukiRequest");
    }

    @Override
    public void handle(SuzukiToken suzukiToken) {
        System.out.println("Handling suzukiToken");
    }

    @Override
    public void handle(ElectionBroadcast electionBroadcast) {
        System.out.println("Handling electionBroadcast");
    }

    @Override
    public void handle(ElectionOK electionOK) {
        System.out.println("Handling electionOK");
    }
}
