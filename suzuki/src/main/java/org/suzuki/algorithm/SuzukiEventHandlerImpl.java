package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.communication.Sender;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventHandler;
import org.suzuki.algorithm.utils.SuzukiRequestBuilder;
import org.suzuki.config.Config;
import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.SuzukiRequest;
import org.suzuki.data.SuzukiToken;
import org.suzuki.data.internal.RequestCS;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SuzukiEventHandlerImpl implements SuzukiEventHandler {

    private SuzukiToken suzukiToken;

    private Suzuki.RunnableWithResource runnableWithResource;

    private int[] RN;

    private int myIndex;

    private Config config;

    private SuzukiRequestBuilder suzukiRequestBuilder;

    private Sender sender;

    // hack until election algorithm is implemented TODO remove
    public SuzukiEventHandlerImpl(Config config, SuzukiToken suzukiToken) {
        this.suzukiToken = suzukiToken;
        initialize(config);
    }

    public SuzukiEventHandlerImpl(Config config) {
        initialize(config);
    }

    private void initialize(Config config) {
        this.config = config;
        this.myIndex = indexOf(config.getMyId());
        this.RN = new int[config.getNodes().size()];

        this.sender = new Sender(config);

        this.suzukiRequestBuilder = new SuzukiRequestBuilder(config);
    }

    @Override
    public void handle(SuzukiRequest suzukiRequest) {
        System.out.println("Handling suzukiRequest...");
        System.out.println(suzukiRequest);

        int processIndex = indexOf(suzukiRequest.getSenderId());

        // max
        int s = suzukiRequest.getValue().getRequestNumber();
        RN[processIndex] = Math.max(RN[processIndex], s);

        // if thing
        if(suzukiToken != null) {  // not in CS cause its single threaded

            if(RN[processIndex] == suzukiToken.numberOf(suzukiRequest.getSenderId()) + 1) {
                sender.send(suzukiRequest.getSenderId(), suzukiToken);

                // TODO remove
                if(RN[processIndex] > suzukiToken.numberOf(suzukiRequest.getSenderId()) + 1) {
                    throw new RuntimeException();
                }
                // TODO remove

                suzukiToken = null;
            }
        }
        System.out.println("Handling suzukiRequest... done." + " RN: " + Arrays.toString(RN));
    }

    @Override
    public void handle(SuzukiToken suzukiToken) {
        System.out.println("Handling suzukiToken...");
        System.out.println(suzukiToken);

        this.suzukiToken = suzukiToken;

        runnableWithResource.run();
        runnableWithResource = null;

        suzukiToken.setNumber(config.getMyId(), RN[myIndex]);

        suzukiToken.appendProcesses(outstandingRequestProcesses(RN, suzukiToken));

        if(!suzukiToken.getValue().getQueue().isEmpty()) {
            int nodeId = suzukiToken.getValue().getQueue().get(0);
            suzukiToken.getValue().getQueue().remove(0);
            suzukiToken.setSenderId(config.getMyId());
            sender.send(nodeId, suzukiToken);
            this.suzukiToken = null;
        }

        System.out.println("Handling suzukiToken... done." + " SuzukiToken: " + suzukiToken);
    }

    @Override
    public void handle(ElectionBroadcast electionBroadcast) {
        throw new UnsupportedOperationException();
//            System.out.println("Handling electionBroadcast");
    }

    @Override
    public void handle(ElectionOK electionOK) {
        throw new UnsupportedOperationException();
//            System.out.println("Handling electionOK");
    }

    @Override
    public void handle(RequestCS requestCS) {
        System.out.println("Handling requestCS...");
        System.out.println(requestCS);

        this.runnableWithResource = requestCS.getRunnableWithResource();
        sender.broadcast(config.getMyId(), suzukiRequestBuilder.build(++RN[myIndex]));

        System.out.println("Handling requestCS... done." + " RN: " + Arrays.toString(RN));
    }

    /**
     * @return outstanding request processes' nodeIds
     */
    private List<Integer> outstandingRequestProcesses(int[] RN, SuzukiToken suzukiToken) {
        List<Integer> result = new LinkedList<>();
        for(int k = 0 ; k < RN.length; ++k) {
            int nodeId = config.getNodeIdFor(k);
            int tokenNumber = suzukiToken.numberOf(nodeId);
            if(RN[k] == tokenNumber + 1) {
                result.add(nodeId);
            }
        }
        return result;
    }

    private int indexOf(int nodeId) {
        return config.getIndexOf(nodeId);
    }

}