package org.suzuki.json;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.suzuki.data.Message;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;
import org.suzuki.util.DataGenerator;

public class MessageParserTest {

    @Test
    public void should_CreateRequestObject_When_ReceivedRequestJson() {
        // given
        SuzukiRequest suzukiRequest = DataGenerator.generateRequest();

        String json = MessageParser.toJson(suzukiRequest);

        // when
        Message message = MessageParser.toObject(json);

        // then
        Assert.assertTrue(message instanceof SuzukiRequest);
        SuzukiRequest casted = (SuzukiRequest) message;
        Assert.assertThat(casted.getSenderId(), CoreMatchers.equalTo(suzukiRequest.getSenderId()));
        Assert.assertThat(casted.getValue().getRequestNumber(),
                CoreMatchers.equalTo(suzukiRequest.getValue().getRequestNumber()));
    }

    @Test
    public void should_CreateTokenObject_When_ReceivedTokenJson() {
        // given
        SuzukiToken suzukiToken = DataGenerator.generateToken();

        String json = MessageParser.toJson(suzukiToken);

        // when
        Message message = MessageParser.toObject(json);

        // then
        Assert.assertTrue(message instanceof SuzukiToken);
        SuzukiToken casted = (SuzukiToken) message;
        Assert.assertThat(casted.getSenderId(), CoreMatchers.equalTo(suzukiToken.getSenderId()));
        Assert.assertThat(casted.getValue().getLastRequests().size(),
                CoreMatchers.equalTo(suzukiToken.getValue().getLastRequests().size()));
        Assert.assertThat(casted.getValue().getQueue().size(),
                CoreMatchers.equalTo(suzukiToken.getValue().getQueue().size()));
    }

//    @Test
//    public void generateConfigJson() {
//        Config config = DataGenerator.generateConfig();
//
//        String json = ConfigParser.toJson(config);
//
//        System.out.println(json);
//
//    }


}
