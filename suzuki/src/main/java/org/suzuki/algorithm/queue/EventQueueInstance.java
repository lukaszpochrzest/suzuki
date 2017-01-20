package org.suzuki.algorithm.queue;

public class EventQueueInstance {

    private static EventQueue eventQueue = new EventQueue();

    //TODO make an "add listener" method rather than "start listening"
    public static void startListening(EventQueueListener eventQueueListener) {
        eventQueue.startListening(eventQueueListener);
    }

    //TODO argument of Event type instead of Object
    public static void put(Object event) {
        eventQueue.put(event);
    }

    //TODO make an "remove listener" method rather than "stop listening"
    public static void stopListening() {
        eventQueue.stopListening();
    }

}
