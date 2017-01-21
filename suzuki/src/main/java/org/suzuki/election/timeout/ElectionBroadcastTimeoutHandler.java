package org.suzuki.election.timeout;

import org.suzuki.data.timeout.ElectionBroadcastTimeout;
import org.suzuki.queue.SuzukiAndElectionAwareEventQueueManager;

public class ElectionBroadcastTimeoutHandler implements Runnable {

    @Override
    public void run() {
        SuzukiAndElectionAwareEventQueueManager.get().put(new ElectionBroadcastTimeout());
    }
}
