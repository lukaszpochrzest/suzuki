package org.suzuki.algorithm.communication;

import org.suzuki.algorithm.communication.tcp.client.TCPClient;
import org.suzuki.config.Config;
import org.suzuki.config.NodeConfig;
import org.suzuki.data.Message;

public class Sender {

    private Config config;

    public Sender(Config config) {
        this.config = config;
    }

    public void broadcast(int myId, Message message) {
        System.out.println("Broadcasting " + message);
        for(NodeConfig nodeConfig : config.getNodes()) {
            if(myId == nodeConfig.getId()) {
                continue;
            }
            // TODO refactor
            TCPClient tcpClient = new TCPClient(nodeConfig.getHost(), nodeConfig.getPort());
            tcpClient.send(message);
        }

    }

    public void send(int senderId, Message message) {

        // TODO refactor his for loop
        for(NodeConfig nodeConfig : config.getNodes()) {
            // TODO refactor
            if(senderId == nodeConfig.getId()) {
                TCPClient tcpClient = new TCPClient(nodeConfig.getHost(), nodeConfig.getPort());
                System.out.println("Sending " + message + " to " + senderId);
                tcpClient.send(message);
                return;
            }
        }

        throw new RuntimeException("No node with id " + senderId + " found in " + config );
    }
}
