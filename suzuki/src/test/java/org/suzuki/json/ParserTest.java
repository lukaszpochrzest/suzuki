package org.suzuki.json;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.suzuki.data.Message;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;
import org.suzuki.util.MessageGenerator;

public class ParserTest {

    @Test
    public void should_CreateRequestObject_When_ReceivedRequestJson() {
        // given
        SuzukiRequest suzukiRequest = MessageGenerator.generateRequest();

        String json = Parser.toJson(suzukiRequest);

        // when
        Message message = Parser.toObject(json);

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
        SuzukiToken suzukiToken = MessageGenerator.generateToken();

        String json = Parser.toJson(suzukiToken);

        // when
        Message message = Parser.toObject(json);

        // then
        Assert.assertTrue(message instanceof SuzukiToken);
        SuzukiToken casted = (SuzukiToken) message;
        Assert.assertThat(casted.getSenderId(), CoreMatchers.equalTo(suzukiToken.getSenderId()));
        Assert.assertThat(casted.getValue().getLastRequests().size(),
                CoreMatchers.equalTo(suzukiToken.getValue().getLastRequests().size()));
        Assert.assertThat(casted.getValue().getQueue().length,
                CoreMatchers.equalTo(suzukiToken.getValue().getQueue().length));
    }


}
