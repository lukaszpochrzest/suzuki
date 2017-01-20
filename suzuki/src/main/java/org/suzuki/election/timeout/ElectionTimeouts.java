package org.suzuki.election.timeout;

import org.suzuki.algorithm.timeout.Timeout;

public class ElectionTimeouts {

    // TODO make it configurable
    private static final int TIMEOUT_ELECTION_BROADCAST = 20000;

    private Timeout electionBroadcastTimeout;

    private ElectionBroadcastTimeoutHandler electionBroadcastTimeoutHandler;

    public ElectionTimeouts() {
        this.electionBroadcastTimeoutHandler = new ElectionBroadcastTimeoutHandler();
    }

    public void startElectionBroadcastTimeout() {
        if(electionBroadcastTimeout != null) {
            //TODO  hmm algorithm works such that it can start multiple broadcasts...
            //TODO  so therese a broadcast flood...
            //TODO  shouldnt we change it
        } else {
            electionBroadcastTimeout = new Timeout();
            electionBroadcastTimeout.executeOnTimeout(electionBroadcastTimeoutHandler, TIMEOUT_ELECTION_BROADCAST);
        }
    }

    public void cancelElectionBroadcastTimeout() {
        electionBroadcastTimeout.cancel();
    }

}