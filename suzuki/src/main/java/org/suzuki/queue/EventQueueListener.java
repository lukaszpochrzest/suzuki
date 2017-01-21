package org.suzuki.queue;

public interface EventQueueListener {

    void handle(Object event);

}
