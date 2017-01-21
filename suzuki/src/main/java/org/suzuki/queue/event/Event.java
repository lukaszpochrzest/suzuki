package org.suzuki.queue.event;

public interface Event {

    void accept(EventVisitor eventVisitor);

}
