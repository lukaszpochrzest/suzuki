package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.queue.event.EventVisitor;

@ToString(callSuper = true, includeFieldNames = false)
public class ElectBroadcast extends Message {

    @Getter
    @Setter
    private ElectBroadcastBody value;

    public ElectBroadcast() {
        this.type = Message.TYPE_ELECT_BROADCAST;
    }

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }
}
