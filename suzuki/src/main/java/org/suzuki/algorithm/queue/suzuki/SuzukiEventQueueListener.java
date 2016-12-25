package org.suzuki.algorithm.queue.suzuki;

import org.suzuki.algorithm.queue.EventQueueListener;

public class SuzukiEventQueueListener implements EventQueueListener {

    private SuzukiEventVisitor suzukiEventVisitor;

    public SuzukiEventQueueListener(SuzukiEventHandler suzukiEventHandler) {
        this.suzukiEventVisitor = new SuzukiEventVisitor(suzukiEventHandler);
    }

    @Override
    public void handle(Object event) {
        SuzukiEvent suzukiEvent = (SuzukiEvent)event;
        suzukiEvent.accept(suzukiEventVisitor);
    }
}
