package org.suzuki.data;

import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString(callSuper = true)
public class ElectionOK extends Message {

    public ElectionOK() {
        this.type = Message.TYPE_ELECTION_OK;
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }

}
