package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ElectionBroadcastBody {

    @Getter
    @Setter
    private Integer nodeId;

    @Override
    public String toString() {
        return "nodeId=" + nodeId;
    }
}
