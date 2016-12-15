package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import org.suzuki.data.visitor.MessageVisitor;

public class SuzukiToken extends Message {

    @Getter
    @Setter
    private SuzukiTokenBody value;

    public SuzukiToken() {
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

}
