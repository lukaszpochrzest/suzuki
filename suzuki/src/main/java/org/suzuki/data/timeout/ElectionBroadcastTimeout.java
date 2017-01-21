package org.suzuki.data.timeout;

import org.suzuki.queue.event.Event;
import org.suzuki.queue.event.EventVisitor;

public class ElectionBroadcastTimeout implements Event {

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }
}
