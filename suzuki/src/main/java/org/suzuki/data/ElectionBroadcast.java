package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString(callSuper = true)
public class ElectionBroadcast extends Message {

    @Getter
    @Setter
    private ElectionBroadcastBody value;

    public ElectionBroadcast() {
        this.type = Message.TYPE_ELECTION_BROADCAST;
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }
}
