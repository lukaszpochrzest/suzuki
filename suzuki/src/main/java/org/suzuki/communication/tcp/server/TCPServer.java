package org.suzuki.communication.tcp.server;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPServer {

    @FunctionalInterface
    public interface JsonMessageHandler {
        void handleJsonMessage(String jsonMessage);
    }

    private JsonMessageHandler jsonMessageHandler;

    private ConcurrentLinkedQueue<String> messageQueue;

    private TCPServerListeningThread tcpServerListeningThread;

    private TCPServerMessageHandlingThread tcpServerMessageHandlingThread;

    public TCPServer(JsonMessageHandler jsonMessageHandler) {
        this.jsonMessageHandler = jsonMessageHandler;
    }

    private void initiate() {
        messageQueue = new ConcurrentLinkedQueue<>();

        tcpServerListeningThread = new TCPServerListeningThread(messageQueue);
        tcpServerListeningThread.setUncaughtExceptionHandler(
                (th,ex) -> {
                    System.out.println("Uncaught exception: " + ex);
                    System.exit(1);
                }
        );

        tcpServerMessageHandlingThread = new TCPServerMessageHandlingThread(jsonMessageHandler, messageQueue);
    }

    public void start() throws IOException {
        initiate();
        tcpServerListeningThread.start();
        tcpServerMessageHandlingThread.start();
    }

    public void close() {
        tcpServerListeningThread.interrupt();
        tcpServerMessageHandlingThread.interrupt();
    }

}
