package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString(callSuper = true)
public class ElectBroadcast extends Message {

    @Getter
    @Setter
    private ElectBroadcastBody value;

    public ElectBroadcast() {
        this.type = Message.TYPE_ELECT_BROADCAST;
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }
}
