package org.suzuki.algorithm.queue.suzuki;

import org.suzuki.data.*;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;

public interface SuzukiEventHandler {
    
    void handle(SuzukiRequest suzukiRequest);

    void handle(SuzukiToken suzukiToken);

    void handle(ElectionBroadcast electionBroadcast);

    void handle(ElectBroadcast electBroadcast);

    void handle(ElectionOK electionOK);

    void handle(RequestCS requestCS);

    void handle(ElectionStart electionStart);

}
