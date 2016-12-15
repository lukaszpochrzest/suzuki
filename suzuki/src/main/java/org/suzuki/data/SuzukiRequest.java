package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import org.suzuki.data.visitor.MessageVisitor;

public class SuzukiRequest extends Message {

    @Getter
    @Setter
    private SuzukiRequestBody value;

    public SuzukiRequest() {
    }


    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

}
