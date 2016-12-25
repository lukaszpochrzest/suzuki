package org.suzuki.data.internal;

import lombok.Getter;
import lombok.ToString;
import org.suzuki.Suzuki;
import org.suzuki.algorithm.queue.suzuki.SuzukiEvent;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString(exclude = "runnableWithResource")
public class RequestCS implements SuzukiEvent {

    @Getter
    private Suzuki.RunnableWithResource runnableWithResource;

    public RequestCS(Suzuki.RunnableWithResource runnableWithResource) {
        this.runnableWithResource = runnableWithResource;
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }
}
