package org.suzuki;

import org.suzuki.algorithm.SuzukiAlgorithm;
import org.suzuki.config.Config;
import org.suzuki.data.SuzukiToken;

public class Suzuki {

    @FunctionalInterface
    public interface RunnableWithResource {
        void run();
    }

    private SuzukiAlgorithm suzukiAlgorithm;


    public Suzuki(Config config) {
        suzukiAlgorithm = new SuzukiAlgorithm(config);
    }

    // hack until election algorithm is implemented TODO remove
    public Suzuki(Config config, SuzukiToken suzukiToken) {
        suzukiAlgorithm = new SuzukiAlgorithm(config, suzukiToken);
    }


    public void launch() {
        suzukiAlgorithm.launch();
    }

    public void executeLocked(RunnableWithResource runnableWithResource) {
        suzukiAlgorithm.executeLocked(runnableWithResource);
    }

    public void close() {
        suzukiAlgorithm.close();
    }

}
