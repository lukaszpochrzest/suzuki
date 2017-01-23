package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.communication.Receiver;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.queue.SuzukiAndElectionAwareEventQueueManager;

public class SuzukiAlgorithm {

    private Receiver receiver;

    public SuzukiAlgorithm() {
        this.receiver = new Receiver();
    }

    public void launch() {
        Config config = ConfigHolder.getConfig();

        SuzukiLogger.setMyId(config.getMyId());
        SuzukiAndElectionAwareEventQueueManager.initialize();
        receiver.launch(config.getPort());
    }

    public void executeLocked(Suzuki.RunnableWithResource runnableWithResource) {
        requestCS(runnableWithResource);
    }

    public void close() {
        receiver.close();
    }

    private void requestCS(Suzuki.RunnableWithResource runnableWithResource) {
        SuzukiAndElectionAwareEventQueueManager.get().put(new RequestCS(runnableWithResource));
    }

}
