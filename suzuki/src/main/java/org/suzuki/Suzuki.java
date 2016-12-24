package org.suzuki;

import org.suzuki.communication.tcp.server.TCPServer;
import org.suzuki.json.JsonToObjectMessageMappingHandler;

public class Suzuki {

    @FunctionalInterface
    public interface RunnableWithResource {
        void run();
    }

    public Suzuki(Integer n) {
        this.RN = new int[n];
    }

    // util data TODO refactor

    private TCPServer tcpServer;

    //  algorithm data TODO refactor

    private final int[] RN;

    public void launch() {
        tcpServer = new TCPServer(new JsonToObjectMessageMappingHandler(new MessageHandlerImpl(RN)));
        tcpServer.start();
    }

    public void executeLocked(RunnableWithResource runnableWithResource) {

        runnableWithResource.run();
    }

    public void close() {
        tcpServer.close();
    }

}
