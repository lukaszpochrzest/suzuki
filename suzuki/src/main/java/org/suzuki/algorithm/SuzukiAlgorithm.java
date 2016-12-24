package org.suzuki.algorithm;

import org.suzuki.MessageHandler;
import org.suzuki.Suzuki;
import org.suzuki.algorithm.communication.Sender;
import org.suzuki.algorithm.communication.tcp.server.TCPServer;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigUtil;
import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;
import org.suzuki.json.JsonToObjectMessageMappingHandler;

public class SuzukiAlgorithm implements MessageHandler {

    // util data TODO refactor

    private TCPServer tcpServer;

    //  algorithm data TODO refactor

    private final int[] RN;

    private int myIndex;

    private Config config;

    //TODO
    private Sender sender = new Sender();

    public SuzukiAlgorithm(Config config) {
        this.config = config;
        this.myIndex = indexOf(config.getMyId());
        this.RN = new int[config.getNodes().size()];
    }

    public void launch() {
        tcpServer = new TCPServer(new JsonToObjectMessageMappingHandler(this));
        tcpServer.start();
    }

    public void executeLocked(Suzuki.RunnableWithResource runnableWithResource) {
        requestCS();
//        runnableWithResource.run();
    }

    public void close() {
        tcpServer.close();
    }

    private void requestCS() {
        ++RN[myIndex];
        //TODO
    }

    @Override
    public void handle(SuzukiRequest suzukiRequest) {
        System.out.println("Handling suzukiRequest");
    }

    @Override
    public void handle(SuzukiToken suzukiToken) {
        System.out.println("Handling suzukiToken");
    }

    @Override
    public void handle(ElectionBroadcast electionBroadcast) {
        System.out.println("Handling electionBroadcast");
    }

    @Override
    public void handle(ElectionOK electionOK) {
        System.out.println("Handling electionOK");
    }

    private int indexOf(int nodeId) {
        return ConfigUtil.getIndexOf(nodeId, config);
    }

}
