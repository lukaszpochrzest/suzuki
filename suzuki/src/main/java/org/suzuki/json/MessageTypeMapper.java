package org.suzuki.json;

import org.suzuki.data.Message;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;

import java.util.HashMap;
import java.util.Map;

public class MessageTypeMapper {

    Map<String, Class<? extends Message>> typeMap;

    public MessageTypeMapper() {
        typeMap = new HashMap<>();

        typeMap.put("request", SuzukiRequest.class);
        typeMap.put("token", SuzukiToken.class);

        //TODO election
    }

    public Class<? extends Message> classOf(String type) {
        Class<? extends Message> clazz = typeMap.get(type);
        if(clazz == null) {
            throw new IllegalArgumentException("Could not filnd class for message type: " + type);
        }
        return clazz;
    }

}
