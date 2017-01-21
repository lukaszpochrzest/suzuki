package org.suzuki.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue {

    private LinkedBlockingQueue<Object> messageQueue;

    private EventQueueListeningThread eventQueueListeningThread;

    //TODO make it possible dla eventqueue doesnt have eventQueueListener
    public EventQueue(EventQueueListener eventQueueListener) {
        this.messageQueue = new LinkedBlockingQueue<>();
        startListening(eventQueueListener);
    }

    public void put(Object event) {
        messageQueue.offer(event);
    }

    public void changeEventQueueListener(EventQueueListener eventQueueListener) {
        eventQueueListeningThread.changeEventQueueListener(eventQueueListener);
    }

    public void close() {
        stopListening();
    }

    private void startListening(EventQueueListener eventQueueListener) {
        if(eventQueueListener == null){
            throw new IllegalArgumentException();
        }
        eventQueueListeningThread = new EventQueueListeningThread(messageQueue, eventQueueListener);
        eventQueueListeningThread.start();
    }

    private void stopListening() {
        eventQueueListeningThread.interrupt();
    }


}