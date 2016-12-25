package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

@ToString
public class SuzukiToken extends Message {

    @Getter
    @Setter
    private SuzukiTokenBody value;

    public SuzukiToken() {
        type = TYPE_TOKEN;
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }

}
