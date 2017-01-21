package org.suzuki.queue.impl;

import org.suzuki.queue.EventQueueListener;
import org.suzuki.queue.event.Event;
import org.suzuki.queue.event.EventHandler;
import org.suzuki.queue.event.EventVisitor;

public class EventQueueListenerImpl implements EventQueueListener {

    private EventVisitor eventVisitor;

    public EventQueueListenerImpl(EventHandler eventHandler) {
        this.eventVisitor = new EventVisitor(eventHandler);
    }

    @Override
    public void handle(Object event) {
        Event suzukiEvent = (Event)event;
        suzukiEvent.accept(eventVisitor);
    }



}
