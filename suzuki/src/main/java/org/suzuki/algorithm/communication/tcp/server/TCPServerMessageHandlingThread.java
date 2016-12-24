package org.suzuki.algorithm.communication.tcp.server;

import java.util.concurrent.LinkedBlockingQueue;

public class TCPServerMessageHandlingThread extends Thread {

    private TCPServer.JsonMessageHandler jsonMessageHandler;

    private LinkedBlockingQueue<String> messageQueue;

    public TCPServerMessageHandlingThread(TCPServer.JsonMessageHandler jsonMessageHandler, LinkedBlockingQueue<String> messageQueue) {
        this.jsonMessageHandler = jsonMessageHandler;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                jsonMessageHandler.handleJsonMessage(messageQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);  //TODO exception handling
            }
        }
    }
}
