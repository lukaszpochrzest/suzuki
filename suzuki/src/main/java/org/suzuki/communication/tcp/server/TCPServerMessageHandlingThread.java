package org.suzuki.communication.tcp.server;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPServerMessageHandlingThread extends Thread {

    private TCPServer.JsonMessageHandler jsonMessageHandler;

    private ConcurrentLinkedQueue<String> messageQueue;

    public TCPServerMessageHandlingThread(TCPServer.JsonMessageHandler jsonMessageHandler, ConcurrentLinkedQueue<String> messageQueue) {
        this.jsonMessageHandler = jsonMessageHandler;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while(true) {
            jsonMessageHandler.handleJsonMessage(messageQueue.poll());
        }
    }
}
