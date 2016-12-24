package org.suzuki.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.suzuki.data.Message;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;

public class MessageParser {

    private static Gson gson = new Gson();

    private static MessageTypeMapper messageTypeMapper = new MessageTypeMapper();

    public static Message toObject(String json) {

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        String type = obj.get("type").getAsString();

        Class<? extends Message> clazz = messageTypeMapper.classOf(type);

        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

}
