package org.suzuki.election;

import org.suzuki.SuzukiForElectionAPI;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.communication.Sender;
import org.suzuki.data.*;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.data.timeout.ElectionBroadcastTimeout;
import org.suzuki.data.timeout.SuzukiTokenTimeout;
import org.suzuki.queue.SuzukiAndElectionAwareEventQueueManager;
import org.suzuki.queue.event.EventHandler;

public class ElectionEventHandler implements EventHandler, ElectedListener, ElectionForSuzukiAPI {

    private final Sender sender;

    private ElectionManager electionManager;

    public ElectionEventHandler() {
        this.sender = new Sender();
    }

    public void setSuzukiForElectionAPI(SuzukiForElectionAPI suzukiForElectionAPI) {
        this.electionManager = new ElectionManager(sender, this, suzukiForElectionAPI);
    }

    @Override
    public void onElected() {
        SuzukiAndElectionAwareEventQueueManager.get().onElectionFinishedElected(electionManager.getRequestCSReceivedDuringElection());
    }

    @Override
    public ElectionNodeState getElectionNodeState() {
        return electionManager.getElectionNodeState();
    }

    @Override
    public void handle(SuzukiRequest suzukiRequest) {
        SuzukiLogger.startEventHandling(suzukiRequest);

        //TODO logging or what?

        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(SuzukiToken suzukiToken) {
        if( electionManager.getElectionNodeState()!= ElectionNodeState.NOT_ELECTING_AT_ALL ) {

            //get rid of token
            SuzukiLogger.log("Received suzukiToken from " + suzukiToken.getSenderId() + " while electing. Removing token.");

        }
    }

    @Override
    public void handle(ElectionBroadcast electionBroadcast) {
        //TODO Logger
        SuzukiLogger.startEventHandling(electionBroadcast);

        electionManager.handle(electionBroadcast);

        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(ElectBroadcast electBroadcast) {
        SuzukiLogger.startEventHandling(electBroadcast);

        electionManager.handle(electBroadcast);

        SuzukiAndElectionAwareEventQueueManager.get().onElectionFinishedNotElected(electionManager.getRequestCSReceivedDuringElection());

        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(ElectionOK electionOK) {
        //TODO logger for election
        SuzukiLogger.startEventHandling(electionOK);

        electionManager.handle(electionOK);

        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(RequestCS requestCS) {
        SuzukiLogger.startEventHandling(requestCS);

        electionManager.handle(requestCS);

        SuzukiLogger.stopEventHandling();
    }

    @Override
    @Deprecated
    public void handle(ElectionStart electionStart) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handle(ElectionBroadcastTimeout electionBroadcastTimeout) {
//        //TODO WHAT A MESS!
        electionManager.elected();
    }

    @Override
    public void handle(SuzukiTokenTimeout suzukiTokenTimeout) {
        electionManager.electionBroadcast();
    }
}
