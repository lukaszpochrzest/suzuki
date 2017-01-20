package org.suzuki.election.timeout;

import org.suzuki.algorithm.queue.EventQueueInstance;
import org.suzuki.data.timeout.ElectionBroadcastTimeout;

public class ElectionBroadcastTimeoutHandler implements Runnable {

    @Override
    public void run() {
        EventQueueInstance.put(new ElectionBroadcastTimeout());
    }
}
