package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString(callSuper = true)
public class SuzukiRequest extends Message {

    @Getter
    @Setter
    private SuzukiRequestBody value;

    public SuzukiRequest() {
        this.type = TYPE_REQUEST;
    }


    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }

}
