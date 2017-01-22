package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ElectBroadcastBody {

    @Getter
    @Setter
    private Integer electNodeId;

    @Override
    public String toString() {
        return "electNodeId=" + electNodeId;
    }
}
