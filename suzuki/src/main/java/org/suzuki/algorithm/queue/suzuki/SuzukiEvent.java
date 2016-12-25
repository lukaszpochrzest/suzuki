package org.suzuki.algorithm.queue.suzuki;

public interface SuzukiEvent {

    void accept(SuzukiEventVisitor suzukiEventVisitor);

}
