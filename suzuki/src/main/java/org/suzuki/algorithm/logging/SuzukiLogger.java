package org.suzuki.algorithm.logging;

import org.suzuki.algorithm.queue.suzuki.SuzukiEvent;

import java.io.PrintStream;

public class SuzukiLogger {

    public static final int DEFAULT_DEEPNESS = 1;

    public static final PrintStream out = System.out;

    private static int deepness = DEFAULT_DEEPNESS;

    public static  void startEventHandling(SuzukiEvent suzukiEvent) {

        deepness = DEFAULT_DEEPNESS;

        out.println(
                "\n"
                + Prefixer.prefixes(deepness)
                + " HANDLING [" + suzukiEvent + "]"
        );

        ++deepness;

    }

    public static void log(String message) {

        out.println(
                Prefixer.prefixes(deepness)
                + " "
                + message
        );

    }

    public static void stopEventHandling() {

        deepness = DEFAULT_DEEPNESS;

        out.println(
                Prefixer.prefixes(deepness)
                + " DONE"
                + "\n"
        );

    }

    private static class Prefixer {

        public static final String PREFIX = "->";

        public static String prefixes(int count) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < count; ++i) {
                sb.append(PREFIX);
            }
            return sb.toString();
        }
    }

}
