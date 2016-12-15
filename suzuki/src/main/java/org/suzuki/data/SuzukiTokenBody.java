package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SuzukiTokenBody {

    @Getter
    @Setter
    private List<SuzukiTokenBodyElement> lastRequests;

}
