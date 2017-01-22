package org.suzuki.algorithm.logging;

import org.suzuki.queue.event.Event;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.suzuki.algorithm.logging.SuzukiLogger.MsgPrefixer.msgPrefixes;

public class SuzukiLogger {

    private static final int DEFAULT_DEEPNESS = 1;

    private static final PrintStream out = System.out;

    private static int deepness = DEFAULT_DEEPNESS;

    private static final LogPrefixer logPrefixer = new LogPrefixer();

    public static void setMyId(Integer myId) {
        logPrefixer.setMyId(myId);
    }

    public static void startEventHandling(Event event) {

        deepness = DEFAULT_DEEPNESS;

        out.println(
//                "\n"
                logPrefixer.logPrefix() + " " + "{" + System.lineSeparator() +
                logPrefixer.logPrefix() + " " + msgPrefixes(deepness) + " HANDLING [" + event + "]"
        );

        ++deepness;

    }

    public static void log(String message) {

        out.println(
                logPrefixer.logPrefix() + " " + msgPrefixes(deepness) + " " + message
        );

    }

    public static void stopEventHandling() {

        deepness = DEFAULT_DEEPNESS;

        out.println(
                logPrefixer.logPrefix() + " " + msgPrefixes(deepness) + " DONE" + System.lineSeparator() +
                logPrefixer.logPrefix() + " " + "}"
//                + "\n"
        );

    }

    static class MsgPrefixer {

        public static final String PREFIX = "\t";

        public static String msgPrefixes(int count) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < count; ++i) {
                sb.append(PREFIX);
            }
            return sb.toString();
        }
    }

    private static class LogPrefixer {

        //<date>:[id]
        private final static String PREFIX_TEMPLATE = "%s:[%s]";

        private final static SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss:SSSS");

        private Integer myId;

        public String logPrefix() {
            return String.format(
                    PREFIX_TEMPLATE,
                    dateFormat.format(new Date()),
                    myId != null ? myId : ""
            );
        }

        public void setMyId(Integer myId) {
            this.myId = myId;
        }
    }

}
