package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.communication.Receiver;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.queue.SuzukiAndElectionAwareEventQueueManager;

public class SuzukiAlgorithm {

    private Receiver receiver;

    private Config config;

    public SuzukiAlgorithm() {
        this.config = ConfigHolder.getConfig();
        this.receiver = new Receiver();
    }


    public void launch() {
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

    //TODO get rid of this
    @Deprecated
    public void triggerElection() {
        SuzukiAndElectionAwareEventQueueManager.get().put(new ElectionStart());
    }

}
