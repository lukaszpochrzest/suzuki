package org.suzuki.algorithm.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue {

    private LinkedBlockingQueue<Object> messageQueue;

    private EventQueueListener eventQueueListener;

    private EventQueueListeningThread eventQueueListeningThread;

    public EventQueue(EventQueueListener eventQueueListener) {
        this.eventQueueListener = eventQueueListener;
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public void startListening() {
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