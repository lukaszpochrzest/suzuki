package org.suzuki.data.internal;

import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEvent;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString
public class ElectionStart implements SuzukiEvent{

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }
}
