package org.suzuki;

import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;

public interface MessageHandler {

    void handle(SuzukiRequest suzukiRequest);

    void handle(SuzukiToken suzukiToken);

    void handle(ElectionBroadcast electionBroadcast);

    void handle(ElectionOK electionOK);

}
