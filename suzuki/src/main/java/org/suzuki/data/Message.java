package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import org.suzuki.data.visitor.MessageVisitor;

public abstract class Message {

    @Getter
    @Setter
    public int senderId;

    @Getter
    @Setter
    public String type;

    public abstract void accept(MessageVisitor messageVisitor);

}
