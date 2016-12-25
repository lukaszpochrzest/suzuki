package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

public class ElectionBroadcast extends Message {

    @Getter
    @Setter
    private ElectionBroadcastBody value;

    public ElectionBroadcast() {
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }
}
