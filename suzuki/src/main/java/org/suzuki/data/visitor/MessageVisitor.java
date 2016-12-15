package org.suzuki.data.visitor;

import org.suzuki.MessageHandler;
import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;

public class MessageVisitor {

    private MessageHandler messageHandler;

    public MessageVisitor(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void visit(SuzukiRequest suzukiRequest) {
        messageHandler.handle(suzukiRequest);
    }

    public void visit(SuzukiToken suzukiToken) {
        messageHandler.handle(suzukiToken);
    }

    public void visit(ElectionBroadcast electionBroadcast) {
        messageHandler.handle(electionBroadcast);
    }

    public void visit(ElectionOK electionOK) {
        messageHandler.handle(electionOK);
    }

}
