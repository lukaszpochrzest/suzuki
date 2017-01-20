package org.suzuki.algorithm.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue {

    private LinkedBlockingQueue<Object> messageQueue;

    private EventQueueListeningThread eventQueueListeningThread;

    public EventQueue() {
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public void startListening(EventQueueListener eventQueueListener) {
        if(eventQueueListeningThread != null) {
            throw new IllegalStateException();
        }
        eventQueueListeningThread = new EventQueueListeningThread(messageQueue, eventQueueListener);
        eventQueueListeningThread.start();
    }

    // TODO
    public void stopListening() {
        eventQueueListeningThread.interrupt();
    }

    public void put(Object event) {
        messageQueue.offer(event);
    }

}