package org.suzuki.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class Config {

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private int myId;

    @Getter
    @Setter
    private List<NodeConfig> nodes;

}
