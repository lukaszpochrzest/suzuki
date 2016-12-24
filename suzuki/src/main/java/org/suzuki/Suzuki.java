package org.suzuki;

import org.suzuki.algorithm.SuzukiAlgorithm;
import org.suzuki.config.Config;

public class Suzuki {

    @FunctionalInterface
    public interface RunnableWithResource {
        void run();
    }

    private SuzukiAlgorithm suzukiAlgorithm;

    public Suzuki(Config config) {
        suzukiAlgorithm = new SuzukiAlgorithm(config);
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
