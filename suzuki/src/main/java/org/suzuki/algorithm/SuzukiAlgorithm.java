package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.communication.Receiver;
import org.suzuki.algorithm.queue.EventQueue;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventQueueListener;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.SuzukiToken;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;

public class SuzukiAlgorithm {

    private Receiver receiver;

    private Config config;

    private EventQueue eventQueue;

    // hack until election algorithm is implemented TODO remove
    public SuzukiAlgorithm(SuzukiToken suzukiToken) {
        this.eventQueue = new EventQueue(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl(suzukiToken)));
        initialize();
    }

    public SuzukiAlgorithm() {
        this.eventQueue = new EventQueue(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl()));
        initialize();
    }

    private void initialize() {
        this.config = ConfigHolder.getConfig();
        this.receiver = new Receiver(eventQueue);
    }

    public void launch() {
        eventQueue.startListening();

        receiver.launch(config.getPort());
    }

    public void executeLocked(Suzuki.RunnableWithResource runnableWithResource) {
        requestCS(runnableWithResource);
    }

    public void close() {
        receiver.close();
    }

    private void requestCS(Suzuki.RunnableWithResource runnableWithResource) {
        eventQueue.put(new RequestCS(runnableWithResource));
    }

    //TODO get rid of this
    @Deprecated
    public void triggerElection() {
        eventQueue.put(new ElectionStart());
    }

}
