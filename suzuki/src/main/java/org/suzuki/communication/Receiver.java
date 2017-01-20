package org.suzuki.communication;

import org.suzuki.algorithm.queue.EventQueueInstance;
import org.suzuki.communication.tcp.server.TCPServer;
import org.suzuki.data.Message;
import org.suzuki.json.MessageParser;

public class Receiver {

    private TCPServer tcpServer;

    public void launch(int port) {
        tcpServer = new TCPServer(
                port,
                jsonMessage -> {
                    Message message = MessageParser.toObject(jsonMessage);
                    EventQueueInstance.put(message);
                }
        );
        tcpServer.start();
    }

    public void close() {
        tcpServer.close();
    }

}
