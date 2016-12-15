package org.suzuki.data;

import org.suzuki.data.visitor.MessageVisitor;

public class ElectionOK extends Message {

    public ElectionOK() {
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

}
