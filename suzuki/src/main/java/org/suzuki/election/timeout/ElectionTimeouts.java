package org.suzuki.election.timeout;

import org.suzuki.config.ConfigHolder;
import org.suzuki.timeout.Timeout;

public class ElectionTimeouts {

    private final int timeoutElectionResponses;

    private Timeout electionBroadcastTimeout;

    private ElectionBroadcastTimeoutHandler electionBroadcastTimeoutHandler;

    public ElectionTimeouts() {
        this.electionBroadcastTimeoutHandler = new ElectionBroadcastTimeoutHandler();
        this.timeoutElectionResponses = ConfigHolder.getConfig().getElectionResponsesTimeout();
    }

    public void startElectionBroadcastTimeout() {
        if(electionBroadcastTimeout != null) {
            //TODO  hmm algorithm works such that it can start multiple broadcasts...
            //TODO  so therese a broadcast flood...
            //TODO  shouldnt we change it
        } else {
            electionBroadcastTimeout = new Timeout();
            electionBroadcastTimeout.executeOnTimeout(electionBroadcastTimeoutHandler, timeoutElectionResponses);
        }
    }

    public void cancelElectionBroadcastTimeout() {
        if(electionBroadcastTimeout != null) {
            electionBroadcastTimeout.cancel();
            electionBroadcastTimeout = null;
        }

    }

}