package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.communication.Receiver;
import org.suzuki.algorithm.queue.EventQueue;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventQueueListener;
import org.suzuki.config.Config;
import org.suzuki.data.SuzukiToken;
import org.suzuki.data.internal.RequestCS;

public class SuzukiAlgorithm {

    private Receiver receiver;

    private Config config;

    private EventQueue eventQueue;
    // hack until election algorithm is implemented TODO remove
    public SuzukiAlgorithm(Config config, SuzukiToken suzukiToken) {
        this.eventQueue = new EventQueue(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl(config, suzukiToken)));
        initialize(config);
    }

    public SuzukiAlgorithm(Config config) {
        this.eventQueue = new EventQueue(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl(config)));
        initialize(config);
    }

    private void initialize(Config config) {
        this.config = config;
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

}
