package org.suzuki.data;

import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

public class ElectionOK extends Message {

    public ElectionOK() {
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }

}
