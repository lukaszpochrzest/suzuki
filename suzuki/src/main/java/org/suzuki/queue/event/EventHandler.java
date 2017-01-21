package org.suzuki.queue.event;

import org.suzuki.data.*;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.data.timeout.ElectionBroadcastTimeout;
import org.suzuki.data.timeout.SuzukiTokenTimeout;

public interface EventHandler {
    
    void handle(SuzukiRequest suzukiRequest);

    void handle(SuzukiToken suzukiToken);

    void handle(ElectionBroadcast electionBroadcast);

    void handle(ElectBroadcast electBroadcast);

    void handle(ElectionOK electionOK);

    void handle(RequestCS requestCS);

    void handle(ElectionStart electionStart);

    void handle(ElectionBroadcastTimeout electionBroadcastTimeout);

    void handle(SuzukiTokenTimeout suzukiTokenTimeout);

}
