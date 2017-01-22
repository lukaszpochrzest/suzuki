package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SuzukiRequestBody {

    @Getter
    @Setter
    private int requestNumber;

    @Override
    public String toString() {
        return "(requestNumber=" + requestNumber + ")";
    }
}
