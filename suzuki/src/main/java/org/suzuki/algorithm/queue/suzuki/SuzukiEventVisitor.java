package org.suzuki.algorithm.queue.suzuki;

import org.suzuki.data.*;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.data.timeout.ElectionBroadcastTimeout;
import org.suzuki.data.timeout.SuzukiTokenTimeout;

public class SuzukiEventVisitor {

    private SuzukiEventHandler suzukiEventHandler;

    public SuzukiEventVisitor(SuzukiEventHandler suzukiEventHandler) {
        this.suzukiEventHandler = suzukiEventHandler;
    }

    public void visit(SuzukiRequest suzukiRequest) {
        suzukiEventHandler.handle(suzukiRequest);
    }

    public void visit(SuzukiToken suzukiToken) {
        suzukiEventHandler.handle(suzukiToken);
    }

    public void visit(ElectionBroadcast electionBroadcast) {
        suzukiEventHandler.handle(electionBroadcast);
    }

    public void visit(ElectBroadcast electBroadcast) {
        suzukiEventHandler.handle(electBroadcast);
    }

    public void visit(ElectionOK electionOK) {
        suzukiEventHandler.handle(electionOK);
    }

    public void visit(RequestCS requestCS) {
        suzukiEventHandler.handle(requestCS);
    }

    public void visit(ElectionStart electionStart) {
        suzukiEventHandler.handle(electionStart);
    }

    public void visit(ElectionBroadcastTimeout electionBroadcastTimeout) {
        suzukiEventHandler.handle(electionBroadcastTimeout);
    }

    public void visit(SuzukiTokenTimeout suzukiTokenTimeout) {
        suzukiEventHandler.handle(suzukiTokenTimeout);
    }

}
