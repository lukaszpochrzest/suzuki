package org.suzuki.data.timeout;

import org.suzuki.queue.event.Event;
import org.suzuki.queue.event.EventVisitor;

public class SuzukiTokenTimeout implements Event {

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }
}
