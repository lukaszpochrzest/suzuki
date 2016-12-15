package org.suzuki.json;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.suzuki.data.Message;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiRequestBody;

public class ParserTest {

    @Test
    public void should_CreateRequestObject_When_ReceivedRequestJson() {
        // given
        SuzukiRequestBody value = new SuzukiRequestBody();
        value.setRequestNumber(11);

        SuzukiRequest suzukiRequest = new SuzukiRequest();
        suzukiRequest.setSenderId(1);
        suzukiRequest.setType("request");
        suzukiRequest.setValue(value);

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

}
