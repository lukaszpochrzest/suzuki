package org.suzuki.algorithm.queue.suzuki;

import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;

public interface SuzukiEventHandler {
    
    void handle(SuzukiRequest suzukiRequest);

    void handle(SuzukiToken suzukiToken);

    void handle(ElectionBroadcast electionBroadcast);

    void handle(ElectionOK electionOK);

    void handle(RequestCS requestCS);

    void handle(ElectionStart electionStart);

}
