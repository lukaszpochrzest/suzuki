package org.suzuki.algorithm.timeout;

import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.config.ConfigHolder;
import org.suzuki.timeout.Timeout;

public class SuzukiTimeouts {

    private final int timeoutReceiveToken;

    private Timeout suzukiTokenTimeout;

    private SuzukiTokenTimeoutHandler suzukiTokenTimeoutHandler;

    public SuzukiTimeouts() {
        this.suzukiTokenTimeoutHandler = new SuzukiTokenTimeoutHandler();
        this.timeoutReceiveToken = ConfigHolder.getConfig().getReceiveTokenTimeout();
    }

    public void startSuzukiTokenTimeout() {
        if(suzukiTokenTimeout != null) {
            SuzukiLogger.log("Token timeuout tried again");
            //TODO  hmm algorithm works such that it can start broadcasts multiple times...
        } else {
            suzukiTokenTimeout = new Timeout();
            suzukiTokenTimeout.executeOnTimeout(suzukiTokenTimeoutHandler, timeoutReceiveToken);
        }
    }

    public void cancelSuzukiTokenTimeout() {
        if(suzukiTokenTimeout != null) {
            suzukiTokenTimeout.cancel();
            suzukiTokenTimeout = null;
        }
    }

}