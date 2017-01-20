package org.suzuki.election;

import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.ElectBroadcast;
import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.election.timeout.ElectionTimeouts;

import java.util.LinkedList;
import java.util.List;

public class ElectionNodeStateManager {

    private ElectionNodeState electionNodeState = ElectionNodeState.NOT_ELECTING_AT_ALL;

    private List<Integer> okList;

    private Config config = ConfigHolder.getConfig();

    private ElectedListener electedListener;

    private ElectionTimeouts electionTimeouts;

    public ElectionNodeStateManager(ElectedListener electedListener, ElectionTimeouts electionTimeouts) {
        this.electedListener = electedListener;
        this.electionTimeouts = electionTimeouts;
    }

    public void updateStateOn(ElectionOK electionOK) {

        // TODO all these below suck. Needs to get rid of these. Algorithm sucks
//        if(okList.contains(electionOK.getSenderId())) {
//            throw new IllegalArgumentException();
//        }
        if(okList == null) {
            return;
        }
        if(okList.contains(electionOK.getSenderId())) {
            return;
        }


        okList.add(electionOK.getSenderId());

        // i've been chosen!
        if(config.getNodes().size() - 1 == okList.size()) {
            setState(ElectionNodeState.NOT_ELECTING_AT_ALL);
            electionTimeouts.cancelElectionBroadcastTimeout();
            electedListener.onElected();
        }

    }

    public void updateStateOn(ElectionBroadcast electionBroadcast, int myId) {

        if(ElectionNodeState.NOT_ELECTING_AT_ALL.equals(electionNodeState)) {
            setState(ElectionNodeState.ELECTION_LISTENING);
        } else if(ElectionNodeState.WAITING_FOR_BROADCAST_RESPONSES.equals(electionNodeState)) {
            if(electionBroadcast.getValue().getNodeId() > myId) {
                setState(ElectionNodeState.ELECTION_LISTENING);
                electionTimeouts.cancelElectionBroadcastTimeout();
            }
        }
//        else {    // ElectionNodeState.ELECTION_LISTENING.equals(electionNodeState)
//            // do nothing
//        }

    }

    public void updateStateOn(ElectBroadcast electBroadcast) {
        setState(ElectionNodeState.NOT_ELECTING_AT_ALL);
    }

    public void onStartElectionListening() {
        setState(ElectionNodeState.ELECTION_LISTENING);
    }

    public void onStartBullying() {
        setState(ElectionNodeState.WAITING_FOR_BROADCAST_RESPONSES);
    }

    private void setState(ElectionNodeState electionNodeState) {

        if(electionNodeState.equals(this.electionNodeState)) {
            if(ElectionNodeState.ELECTION_LISTENING.equals(electionNodeState)) {
                throw new RuntimeException("Ineffective change from " + electionNodeState + " to " + this.electionNodeState);
            } else if (ElectionNodeState.NOT_ELECTING_AT_ALL.equals(electionNodeState)) {
                throw new RuntimeException("Ineffective change from " + electionNodeState + " to " + this.electionNodeState);
            }
//            else {    // ElectionNodeState.WAITING_FOR_BROADCAST_RESPONSESOT_ELECTING_AT_ALL.equals(electionNodeState)
//                  // this may happen
//            }
        }

        if(ElectionNodeState.WAITING_FOR_BROADCAST_RESPONSES.equals(electionNodeState)) {

            okList = new LinkedList<>();

        } else if(  ElectionNodeState.ELECTION_LISTENING.equals(electionNodeState) ||
                    ElectionNodeState.NOT_ELECTING_AT_ALL.equals(electionNodeState) ){

            okList = null;

        }

        this.electionNodeState = electionNodeState;

    }

}
