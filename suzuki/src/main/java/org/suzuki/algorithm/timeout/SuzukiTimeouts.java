package org.suzuki.algorithm.timeout;

import org.suzuki.timeout.Timeout;

public class SuzukiTimeouts {

    // TODO make it configurable
    private static final int TIMEOUT_SUZUKI_TOKEN = 40000;

    private Timeout suzukiTokenTimeout;

    private SuzukiTokenTimeoutHandler suzukiTokenTimeoutHandler;

    public SuzukiTimeouts() {
        this.suzukiTokenTimeoutHandler = new SuzukiTokenTimeoutHandler();
    }

    public void startSuzukiTokenTimeout() {
        if(suzukiTokenTimeout != null) {
            System.out.println("Token timeuout tried again");
            //TODO  hmm algorithm works such that it can start broadcasts multiple times...
        } else {
            suzukiTokenTimeout = new Timeout();
            suzukiTokenTimeout.executeOnTimeout(suzukiTokenTimeoutHandler, TIMEOUT_SUZUKI_TOKEN);
        }
    }

    public void cancelSuzukiTokenTimeout() {
        if(suzukiTokenTimeout != null) {
            suzukiTokenTimeout.cancel();
            suzukiTokenTimeout = null;
        }
    }

}