package org.suzuki.queue.event;

import org.suzuki.data.*;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.data.timeout.ElectionBroadcastTimeout;
import org.suzuki.data.timeout.SuzukiTokenTimeout;

public class EventVisitor {

    private EventHandler eventHandler;

    public EventVisitor(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void visit(SuzukiRequest suzukiRequest) {
        eventHandler.handle(suzukiRequest);
    }

    public void visit(SuzukiToken suzukiToken) {
        eventHandler.handle(suzukiToken);
    }

    public void visit(ElectionBroadcast electionBroadcast) {
        eventHandler.handle(electionBroadcast);
    }

    public void visit(ElectBroadcast electBroadcast) {
        eventHandler.handle(electBroadcast);
    }

    public void visit(ElectionOK electionOK) {
        eventHandler.handle(electionOK);
    }

    public void visit(RequestCS requestCS) {
        eventHandler.handle(requestCS);
    }

    public void visit(ElectionStart electionStart) {
        eventHandler.handle(electionStart);
    }

    public void visit(ElectionBroadcastTimeout electionBroadcastTimeout) {
        eventHandler.handle(electionBroadcastTimeout);
    }

    public void visit(SuzukiTokenTimeout suzukiTokenTimeout) {
        eventHandler.handle(suzukiTokenTimeout);
    }

}
