package org.suzuki;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.app.resource.Resource;
import org.suzuki.config.ConfigHolder;
import org.suzuki.config.ConfigParser;
import org.suzuki.config.exception.ConfigParseException;
import org.suzuki.app.ui.CmdLineParser;
import org.suzuki.app.ui.SuzukiLogo;
import org.suzuki.app.ui.exception.NoConfigException;
import org.suzuki.app.ui.io.FileReader;
import org.suzuki.app.ui.io.exception.FileReadException;

import java.io.Console;
import java.io.IOException;

import static org.suzuki.app.ui.CmdLineParser.*;

public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        app.run(args);
    }

    private final Resource herokuResource = new Resource(Resource.RESOURCE_URL_HEROKU);

    private Suzuki suzuki;

    private CommandLine line;

    public void run(String[] args) {
        System.out.println(SuzukiLogo.SUZUKI_LOGO);

        try {
            readConfig(args);

            suzuki = new Suzuki();
            suzuki.launch();

            Console console = System.console();
            while(true) {
                String s = console.readLine();

                if("exit".equals(s)) {
                    exit();
                } else if("r".equals(s)) {
                    suzuki.executeLocked(this::accessResource);
                } else {
                    System.out.println("No such command");
                }
            }

        } catch (ParseException e) {
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

    private void readConfig(String args[]) throws ParseException {
        CmdLineParser cmdLineParser = new CmdLineParser();
        line = cmdLineParser.getLine(args);

        if (!line.hasOption(ARG_CONFIG)) {
            throw new NoConfigException();
        }

        String configFile = FileReader.read(line.getOptionValue(ARG_CONFIG));
        ConfigHolder.setConfig(ConfigParser.parse(configFile));
    }

    private void accessResource() {
        if (line.hasOption(ARG_FAKE)) {
            accessFake();
        } else {
            accessHeroku();
        }
    }

    private void accessHeroku() {
        try {
            herokuResource.get();
            herokuResource.set(1);
            herokuResource.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accessFake() {
        try {
            SuzukiLogger.log("Accessing resource... ");
            Thread.sleep(10000);
            SuzukiLogger.log("Accessing resource... done.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void exit() {
        suzuki.close();
        System.exit(0);
    }

}
