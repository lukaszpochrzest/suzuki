package org.suzuki.algorithm.queue.suzuki;

import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;
import org.suzuki.data.internal.RequestCS;

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

    public void visit(ElectionOK electionOK) {
        suzukiEventHandler.handle(electionOK);
    }

    public void visit(RequestCS requestCS) {
        suzukiEventHandler.handle(requestCS);
    }

}
