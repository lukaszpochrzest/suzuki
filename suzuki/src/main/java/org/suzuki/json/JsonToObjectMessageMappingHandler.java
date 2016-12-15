package org.suzuki.json;

import org.suzuki.MessageHandler;
import org.suzuki.communication.tcp.server.TCPServer;
import org.suzuki.data.Message;
import org.suzuki.data.visitor.MessageVisitor;

public class JsonToObjectMessageMappingHandler implements TCPServer.JsonMessageHandler {

    private MessageVisitor messageVisitor;

    public JsonToObjectMessageMappingHandler(MessageHandler messageHandler) {
        this.messageVisitor = new MessageVisitor(messageHandler);
    }

    @Override
    public void handleJsonMessage(String jsonMessage) {
        Message messageObject = Parser.toObject(jsonMessage);

        messageObject.accept(messageVisitor);
    }
}