package org.suzuki.data.internal;

import lombok.ToString;
import org.suzuki.queue.event.Event;
import org.suzuki.queue.event.EventVisitor;

@ToString(includeFieldNames = false)
public class ElectionStart implements Event {

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }
}
