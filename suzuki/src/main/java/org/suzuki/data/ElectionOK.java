package org.suzuki.data;

import lombok.ToString;
import org.suzuki.queue.event.EventVisitor;

@ToString(callSuper = true)
public class ElectionOK extends Message {

    public ElectionOK() {
        this.type = Message.TYPE_ELECTION_OK;
    }

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }

}
