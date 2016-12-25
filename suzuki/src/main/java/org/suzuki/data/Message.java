package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import org.suzuki.algorithm.queue.suzuki.SuzukiEvent;

public abstract class Message implements SuzukiEvent {

    // TODO make some enum
    public static final String TYPE_REQUEST = "request";
    public static final String TYPE_TOKEN = "token";
    // TODO more

    @Getter
    @Setter
    public int senderId;

    @Getter
    @Setter
    public String type;

}
