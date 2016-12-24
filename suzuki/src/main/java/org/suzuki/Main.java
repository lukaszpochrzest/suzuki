package org.suzuki;

import org.suzuki.communication.tcp.client.TCPClient;
import org.suzuki.communication.tcp.server.TCPServerListeningThread;
import org.suzuki.json.Parser;
import org.suzuki.util.MessageGenerator;

import java.io.Console;

public class Main {

    public static void main(String[] args) {

        //TODO
//        Config config = new Config();
//        Integer n = config.getNodes().size();
        Integer n = 2;

        Suzuki suzuki = new Suzuki(n);  //TODO arg
        suzuki.launch();

        TCPClient tcpClient = new TCPClient("localhost", TCPServerListeningThread.PORT);    //TODO arguments

        Console console = System.console();
        while(true) {
            String s = console.readLine();

            // options here TODO refactor

            if("exit".equals(s)) {
                suzuki.close();
                System.exit(0);
            } else if("request".equals(s)) {
                String suzukiRequestJson = Parser.toJson(MessageGenerator.generateRequest());
                tcpClient.send(suzukiRequestJson);
            } else {
                System.out.println("No such command");
            }
        }

    }

}
