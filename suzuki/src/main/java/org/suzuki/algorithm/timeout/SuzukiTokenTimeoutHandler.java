package org.suzuki.algorithm.timeout;

import org.suzuki.data.timeout.SuzukiTokenTimeout;
import org.suzuki.queue.SuzukiAndElectionAwareEventQueueManager;

public class SuzukiTokenTimeoutHandler implements Runnable {

    @Override
    public void run() {
        SuzukiAndElectionAwareEventQueueManager.get().put(new SuzukiTokenTimeout());
    }
}
