package org.suzuki.communication;

import org.suzuki.communication.tcp.server.TCPServer;
import org.suzuki.data.Message;
import org.suzuki.json.MessageParser;
import org.suzuki.queue.SuzukiAndElectionAwareEventQueueManager;

public class Receiver {

    private TCPServer tcpServer;

    public void launch(int port) {
        tcpServer = new TCPServer(
                port,
                jsonMessage -> {
                    Message message = MessageParser.toObject(jsonMessage);
                    SuzukiAndElectionAwareEventQueueManager.get().put(message);
                }
        );
        tcpServer.start();
    }

    public void close() {
        tcpServer.close();
    }

}
