package org.suzuki.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class EventQueueListeningThread extends Thread {

    private EventQueueListener eventQueueListener;

    private LinkedBlockingQueue<Object> messageQueue;

    public EventQueueListeningThread(LinkedBlockingQueue<Object> messageQueue, EventQueueListener eventQueueListener) {
        this.eventQueueListener = eventQueueListener;
        this.messageQueue = messageQueue;
    }

    // TODO ThreadLocal
    /**
     * may be used only by curr thread
     * @param eventQueueListener
     */
    public void changeEventQueueListener(EventQueueListener eventQueueListener) {
        if(eventQueueListener == null) {
            throw new IllegalArgumentException();
        }
        this.eventQueueListener = eventQueueListener;
        System.out.print("\n\tCHANGED");
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