package org.suzuki;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.communication.tcp.client.TCPClient;
import org.suzuki.config.ConfigHolder;
import org.suzuki.config.ConfigParser;
import org.suzuki.config.exception.ConfigParseException;
import org.suzuki.json.MessageParser;
import org.suzuki.ui.CmdLineParser;
import org.suzuki.ui.SuzukiLogo;
import org.suzuki.ui.exception.NoConfigException;
import org.suzuki.ui.io.FileReader;
import org.suzuki.ui.io.exception.FileReadException;
import org.suzuki.util.DataGenerator;

import java.io.Console;

public class Main {

    public static void main(String[] args) {
        System.out.println(SuzukiLogo.SUZUKI_LOGO);

        try {
            CmdLineParser cmdLineParser = new CmdLineParser();
            CommandLine line = cmdLineParser.getLine(args);

            if (!line.hasOption(CmdLineParser.ARG_CONFIG)) {
                throw new NoConfigException();
            }

            String configFile = FileReader.read(line.getOptionValue(CmdLineParser.ARG_CONFIG));
            ConfigHolder.setConfig(ConfigParser.parse(configFile));
            // TODO
//                ConfigValidator.validate(config);

            Suzuki suzuki = new Suzuki();
            suzuki.launch();

            TCPClient tcpClient = new TCPClient("localhost", ConfigHolder.getConfig().getPort());    //TODO arguments

            Console console = System.console();
            while(true) {
                String s = console.readLine();

                // options here TODO refactor

                if("exit".equals(s)) {
                    suzuki.close();
                    System.exit(0);
                } else if("r".equals(s)) {
                    suzuki.executeLocked(() -> {
                        try {
                            SuzukiLogger.log("Accessing resource... ");
                            Thread.sleep(20000);
                            SuzukiLogger.log("Accessing resource... done.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                } else if("elect".equals(s)) {
                    suzuki.triggerElection();
                } else if("debugRequest".equals(s)) {
                    String suzukiRequestJson = MessageParser.toJson(DataGenerator.generateRequest());
                    tcpClient.send(suzukiRequestJson);
                } else {
                    System.out.println("No such command");
                }
            }

        } catch (ParseException e) {    //TODO get rid of this block ?
            e.printStackTrace();
            System.exit(1);
        } catch (FileReadException e) {
            System.err.println("Fail to read config file");
            System.exit(2);
        } catch (ConfigParseException e) {
            System.err.println("Fail to parse config file");
            System.exit(3);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(4);
        }

    }

}
