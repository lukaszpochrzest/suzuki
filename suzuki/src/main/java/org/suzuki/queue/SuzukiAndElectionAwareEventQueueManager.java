package org.suzuki.queue;

import org.suzuki.algorithm.SuzukiEventHandler;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.timeout.SuzukiTokenTimeout;
import org.suzuki.election.ElectedListener;
import org.suzuki.election.ElectionEventHandler;
import org.suzuki.queue.impl.EventQueueListenerImpl;

public class SuzukiAndElectionAwareEventQueueManager {

    /** static things for external world **/ //TODO move it out somewhere

    private static SuzukiAndElectionAwareEventQueueManager suzukiAndElectionAwareEventQueueManager;

    public static void initialize() {
        suzukiAndElectionAwareEventQueueManager = new SuzukiAndElectionAwareEventQueueManager();
    }

    public static SuzukiAndElectionAwareEventQueueManager get() {
        if(suzukiAndElectionAwareEventQueueManager == null) {
            throw new IllegalStateException();
        }
        return suzukiAndElectionAwareEventQueueManager;
    }

    /** mode **/


    private static final SuzukiEventHandler suzukiEventHandler;
    private static final ElectionEventHandler electionEventHandler;

    static {
        suzukiEventHandler = new SuzukiEventHandler();
        electionEventHandler = new ElectionEventHandler();

        suzukiEventHandler.setElectionForSuzukiAPI(electionEventHandler);
        electionEventHandler.setSuzukiForElectionAPI(suzukiEventHandler);

        SUZUKI_EVENT_QUEUE_LISTENER = new EventQueueListenerImpl(suzukiEventHandler);
        ELECTION_EVENT_QUEUE_LISTENER = new EventQueueListenerImpl(electionEventHandler);
    }

    private static final EventQueueListener SUZUKI_EVENT_QUEUE_LISTENER;
    private static final EventQueueListener ELECTION_EVENT_QUEUE_LISTENER;

    public enum Mode {
        SUZUKI(SUZUKI_EVENT_QUEUE_LISTENER),
        ELECTION(ELECTION_EVENT_QUEUE_LISTENER);


        private final EventQueueListener eventQueueListener;
        Mode(EventQueueListener eventQueueListener) {
            this.eventQueueListener = eventQueueListener;
        }
        private EventQueueListener eventQueueListener() { return eventQueueListener; }
    }

    /** class def **/

    private static Mode INITIAL_MODE = Mode.ELECTION;

    private EventQueue eventQueue;

    private Mode mode;

    private SuzukiAndElectionAwareEventQueueManager() {
        mode = INITIAL_MODE;
        initializeQueue(mode.eventQueueListener);
    }

    public void initializeQueue(org.suzuki.queue.EventQueueListener eventQueueListener) {
        eventQueue = new EventQueue(eventQueueListener);
    }

    //TODO argument of Event type instead of Object
    public void put(Object event) {
        eventQueue.put(event);
    }

    //TODO ThreadLocal
    public void onElectionFinishedElected() {
        switchTo(Mode.SUZUKI);

        //TODO should directly access handler handler here
        suzukiEventHandler.onElected();
    }

    //TODO ThreadLocal
    public void onElectionFinishedNotElected() {
        switchTo(Mode.SUZUKI);
    }

    //TODO ThreadLocal
    public void onSwitchToElection(ElectionBroadcast electionBroadcast) {
        switchTo(Mode.ELECTION);
        mode.eventQueueListener.handle(electionBroadcast);
    }

    //TODO ThreadLocal
    public void onElectionNecessary(SuzukiTokenTimeout suzukiTokenTimeout) {
        switchTo(Mode.ELECTION);
        mode.eventQueueListener.handle(suzukiTokenTimeout);
    }

    public void close() {
        eventQueue.close();
    }

    private void switchTo(Mode mode) {
        if(this.mode.equals(mode)) {
            throw new IllegalArgumentException();
        }
        this.mode = mode;
        SuzukiLogger.log("Switching to " + this.mode.name());
        eventQueue.changeEventQueueListener(this.mode.eventQueueListener);
    }

}