package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import org.suzuki.data.visitor.MessageVisitor;

public class ElectionBroadcast extends Message {

    @Getter
    @Setter
    private ElectionBroadcastBody value;

    public ElectionBroadcast() {
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
