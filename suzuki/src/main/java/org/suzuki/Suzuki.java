package org.suzuki;

import org.suzuki.communication.tcp.server.TCPServer;
import org.suzuki.json.JsonToObjectMessageMappingHandler;

import java.io.Console;

public class Suzuki {

    @FunctionalInterface
    public interface RunnableWithResource {
        void run();
    }

    private TCPServer tcpServer;

    public void launch() {
        tcpServer = new TCPServer(new JsonToObjectMessageMappingHandler(new MessageHandlerImpl()));
    }

    public void executeLocked(RunnableWithResource runnableWithResource) {

        runnableWithResource.run();
    }

    public void close() {
        tcpServer.close();
    }

    public static void main(String[] args) {
        Suzuki suzuki = new Suzuki();
        suzuki.launch();

        Console console = System.console();
        while(true) {
            String s = console.readLine();
            if("exit".equals(s)) {
                suzuki.close();
                System.exit(0);
            }
        }

    }

}
