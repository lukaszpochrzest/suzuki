package org.suzuki.json;

import org.suzuki.data.*;

import java.util.HashMap;
import java.util.Map;

public class MessageTypeMapper {

    Map<String, Class<? extends Message>> typeMap;

    public MessageTypeMapper() {
        typeMap = new HashMap<>();
        typeMap.put(Message.TYPE_REQUEST, SuzukiRequest.class);
        typeMap.put(Message.TYPE_TOKEN, SuzukiToken.class);
        typeMap.put(Message.TYPE_ELECTION_BROADCAST, ElectionBroadcast.class);
        typeMap.put(Message.TYPE_ELECT_BROADCAST, ElectBroadcast.class);
        typeMap.put(Message.TYPE_ELECTION_OK, ElectionOK.class);
    }

    public Class<? extends Message> classOf(String type) {
        Class<? extends Message> clazz = typeMap.get(type);
        if(clazz == null) {
            throw new IllegalArgumentException("Could not filnd class for message type: " + type);
        }
        return clazz;
    }

}
