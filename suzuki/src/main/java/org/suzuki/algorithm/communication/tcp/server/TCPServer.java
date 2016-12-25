package org.suzuki.algorithm.communication.tcp.server;

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

    private int port;

    public TCPServer(int port, JsonMessageHandler jsonMessageHandler) {
        this.port = port;
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

        //TODO message queue seems to be unnecessery now. we can push all messages to event queue directly

        messageQueue = new LinkedBlockingQueue<>();

        tcpServerListeningThread = new TCPServerListeningThread(port, messageQueue);
        tcpServerListeningThread.setUncaughtExceptionHandler(
                (th,ex) -> {
                    System.out.println("Uncaught exception: " + ex);
                    System.exit(1);
                }
        );

        tcpServerMessageHandlingThread = new TCPServerMessageHandlingThread(jsonMessageHandler, messageQueue);
    }

}
