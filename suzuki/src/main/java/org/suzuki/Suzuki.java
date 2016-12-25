package org.suzuki;

import org.suzuki.algorithm.SuzukiAlgorithm;
import org.suzuki.data.SuzukiToken;

public class Suzuki {

    @FunctionalInterface
    public interface RunnableWithResource {
        void run();
    }

    private SuzukiAlgorithm suzukiAlgorithm;


    public Suzuki() {
        suzukiAlgorithm = new SuzukiAlgorithm();
    }

    // hack until election algorithm is implemented TODO remove
    public Suzuki(SuzukiToken suzukiToken) {
        suzukiAlgorithm = new SuzukiAlgorithm(suzukiToken);
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

    @Deprecated
    public void triggerElection() {
        suzukiAlgorithm.triggerElection();
    }

}
