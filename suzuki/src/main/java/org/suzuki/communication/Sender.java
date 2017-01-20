package org.suzuki.communication;

import org.suzuki.communication.tcp.client.Exception.SendException;
import org.suzuki.communication.tcp.client.TCPClient;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.config.NodeConfig;
import org.suzuki.data.Message;

public class Sender {

    private Config config;

    public Sender() {
        this.config = ConfigHolder.getConfig();
    }

    public void broadcast(int myId, Message message) {
        SuzukiLogger.log("Broadcasting " + message);
        for(NodeConfig nodeConfig : config.getNodes()) {
            if(myId == nodeConfig.getId()) {
                continue;
            }
            // TODO refactor
            try{
                TCPClient tcpClient = new TCPClient(nodeConfig.getHost(), nodeConfig.getPort());
                tcpClient.send(message);
            } catch (SendException e) {
                // TODO rethink
                e.printStackTrace();
            }
        }
    }

    public void send(int senderId, Message message) {

        // TODO refactor his for loop
        for(NodeConfig nodeConfig : config.getNodes()) {
            // TODO refactor
            if(senderId == nodeConfig.getId()) {
                try {
                    TCPClient tcpClient = new TCPClient(nodeConfig.getHost(), nodeConfig.getPort());
                    SuzukiLogger.log("Sending " + message + " to " + senderId);
                    tcpClient.send(message);
                } catch (SendException e) {
                    // TODO rethink
                    e.printStackTrace();
                }
                return;
            }
        }

        throw new RuntimeException("No node with id " + senderId + " found in " + config );
    }
}
