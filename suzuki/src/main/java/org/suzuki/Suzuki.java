package org.suzuki;

import org.suzuki.communication.tcp.server.TCPServer;
import org.suzuki.json.JsonToObjectMessageMappingHandler;

public class Suzuki {

    @FunctionalInterface
    public interface RunnableWithResource {
        void run();
    }

    private TCPServer tcpServer;

    public void launch() {
        tcpServer = new TCPServer(new JsonToObjectMessageMappingHandler(new MessageHandlerImpl()));
        tcpServer.start();
    }

    public void executeLocked(RunnableWithResource runnableWithResource) {

        runnableWithResource.run();
    }

    public void close() {
        tcpServer.close();
    }

}
