package org.suzuki.algorithm.timeout;

import org.suzuki.algorithm.queue.EventQueueInstance;
import org.suzuki.data.timeout.ElectionBroadcastTimeout;
import org.suzuki.data.timeout.SuzukiTokenTimeout;

public class SuzukiTokenTimeoutHandler implements Runnable {

    @Override
    public void run() {
        EventQueueInstance.put(new SuzukiTokenTimeout());
    }
}
