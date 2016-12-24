package org.suzuki.communication.tcp.server;

import java.util.concurrent.LinkedBlockingQueue;

public class TCPServer {

    @FunctionalInterface
    public interface JsonMessageHandler {
        void handleJsonMessage(String jsonMessage);
    }

    private JsonMessageHandler jsonMessageHandler;

    private LinkedBlockingQueue<String> messageQueue;

    private TCPServerListeningThread tcpServerListeningThread;

    private TCPServerMessageHandlingThread tcpServerMessageHandlingThread;

    public TCPServer(JsonMessageHandler jsonMessageHandler) {
        this.jsonMessageHandler = jsonMessageHandler;
    }

    public void start() {
        initiate();
        tcpServerListeningThread.start();
        tcpServerMessageHandlingThread.start();
    }

    public void close() {
        tcpServerListeningThread.interrupt();
        tcpServerMessageHandlingThread.interrupt();
    }

    private void initiate() {
        messageQueue = new LinkedBlockingQueue<>();

        tcpServerListeningThread = new TCPServerListeningThread(messageQueue);
        tcpServerListeningThread.setUncaughtExceptionHandler(
                (th,ex) -> {
                    System.out.println("Uncaught exception: " + ex);
                    System.exit(1);
                }
        );

        tcpServerMessageHandlingThread = new TCPServerMessageHandlingThread(jsonMessageHandler, messageQueue);
    }

}
