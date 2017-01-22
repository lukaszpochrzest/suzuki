package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.queue.event.EventVisitor;

@ToString(callSuper = true, includeFieldNames = false)
public class ElectionBroadcast extends Message {

    @Getter
    @Setter
    private ElectionBroadcastBody value;

    public ElectionBroadcast() {
        this.type = Message.TYPE_ELECTION_BROADCAST;
    }

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }
}
