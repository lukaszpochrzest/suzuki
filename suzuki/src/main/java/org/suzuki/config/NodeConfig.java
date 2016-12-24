package org.suzuki.config;

import lombok.Getter;
import lombok.Setter;

public class NodeConfig {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private int port;

}
