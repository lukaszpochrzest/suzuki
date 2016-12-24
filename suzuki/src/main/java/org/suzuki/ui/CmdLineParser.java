package org.suzuki.ui;

import org.apache.commons.cli.*;

public class CmdLineParser {

    public static final String ARG_CONFIG = "config";

    private Options options = new Options();

    private HelpFormatter formatter = new HelpFormatter();

    private CommandLineParser parser = new DefaultParser();

    public CmdLineParser() {
        options.addOption(
                Option.builder()
                        .longOpt(ARG_CONFIG)
                        .hasArg()
                        .argName("file")
                        .desc("config file")
                        .build());
    }

    public CommandLine getLine(String[] args) throws ParseException {
        return parser.parse( options, args );
    }

    public void printHelp() {
        formatter.printHelp( "wcc", options );
    }
}
