package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.queue.event.Event;

@ToString
public abstract class Message implements Event {

    // TODO make some enum
    public static final String TYPE_REQUEST = "request";
    public static final String TYPE_TOKEN = "token";
    public static final String TYPE_ELECTION_BROADCAST = "electionBroadcast";
    public static final String TYPE_ELECT_BROADCAST = "electBroadcast";
    public static final String TYPE_ELECTION_OK = "electionOK";


    @Getter
    @Setter
    public int senderId;

    @Getter
    @Setter
    public String type;

}
