package org.suzuki.algorithm.communication;

import org.suzuki.algorithm.communication.tcp.server.TCPServer;
import org.suzuki.algorithm.queue.EventQueue;
import org.suzuki.data.Message;
import org.suzuki.json.MessageParser;

public class Receiver {

    private TCPServer tcpServer;

    private EventQueue eventQueue;

    public Receiver(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public void launch(int port) {
        tcpServer = new TCPServer(
                port,
                jsonMessage -> {
                    Message message = MessageParser.toObject(jsonMessage);
                    eventQueue.put(message);
                }
        );
        tcpServer.start();
    }

    public void close() {
        tcpServer.close();
    }

}
