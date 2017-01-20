package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.queue.EventQueueInstance;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventQueueListener;
import org.suzuki.communication.Receiver;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;

public class SuzukiAlgorithm {

    private Receiver receiver;

    private Config config;

    public SuzukiAlgorithm() {
        this.config = ConfigHolder.getConfig();
        this.receiver = new Receiver();
    }


    public void launch() {
        EventQueueInstance.startListening(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl()));

        receiver.launch(config.getPort());
    }

    public void executeLocked(Suzuki.RunnableWithResource runnableWithResource) {
        requestCS(runnableWithResource);
    }

    public void close() {
        receiver.close();
    }

    private void requestCS(Suzuki.RunnableWithResource runnableWithResource) {
        EventQueueInstance.put(new RequestCS(runnableWithResource));
    }

    //TODO get rid of this
    @Deprecated
    public void triggerElection() {
        EventQueueInstance.put(new ElectionStart());
    }

}
