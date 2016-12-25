package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class SuzukiTokenBody {

    @Getter
    @Setter
    private List<SuzukiTokenBodyElement> lastRequests;

    @Getter
    @Setter
    private List<Integer> queue;

}
