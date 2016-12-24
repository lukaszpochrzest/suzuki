package org.suzuki;

import org.suzuki.config.Config;
import org.suzuki.config.NodeConfig;

import java.util.Arrays;

public class SuzukiTest {

    //TODO make it a test
    public void launch() {
        NodeConfig nodeConfig = new NodeConfig();
        nodeConfig.setId(1);
        nodeConfig.setPort(2345);
        nodeConfig.setHost("SHOULD_NOT_USE_THAT");
        //TODO

        Config config = new Config();
        config.setMyId(1);
        config.setPort(2345);
        config.setNodes(Arrays.asList());

        //TODO
//        Suzuki suzuki = new Suzuki(1,2);  //TODO arg

//        suzuki.executeLocked(() -> System.out.println("Doing resource"));
    }

}