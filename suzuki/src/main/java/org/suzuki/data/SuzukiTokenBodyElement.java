package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SuzukiTokenBodyElement {

    @Getter
    @Setter
    private Integer nodeId;

    @Getter
    @Setter
    private Integer number;

    @Override
    public String toString() {
        return "(nodeId=" + nodeId + ":" + number + ")";
    }
}
