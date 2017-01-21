package org.suzuki.data.internal;

import lombok.Getter;
import lombok.ToString;
import org.suzuki.Suzuki;
import org.suzuki.queue.event.Event;
import org.suzuki.queue.event.EventVisitor;

@ToString(exclude = "runnableWithResource")
public class RequestCS implements Event {

    @Getter
    private Suzuki.RunnableWithResource runnableWithResource;

    public RequestCS(Suzuki.RunnableWithResource runnableWithResource) {
        this.runnableWithResource = runnableWithResource;
    }

    @Override
    public void accept(EventVisitor eventVisitor) {
        eventVisitor.visit(this);
    }
}
