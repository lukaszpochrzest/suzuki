package org.suzuki.algorithm.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class EventQueueListeningThread extends Thread {

    private EventQueueListener eventQueueListener;

    private LinkedBlockingQueue<Object> messageQueue;

    public EventQueueListeningThread(LinkedBlockingQueue<Object> messageQueue, EventQueueListener eventQueueListener) {
        this.eventQueueListener = eventQueueListener;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                eventQueueListener.handle(messageQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);  //TODO exception handling
            }
        }
    }
}