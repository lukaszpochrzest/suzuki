package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.communication.Sender;
import org.suzuki.algorithm.logging.SuzukiLogger;
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
import java.util.Map;

public class SuzukiEventHandlerImpl implements SuzukiEventHandler {

    private SuzukiToken suzukiToken;

    private Suzuki.RunnableWithResource runnableWithResource;

    private RN RN;

    private int myId;

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
        this.myId = config.getMyId();
        this.RN = new RN(config);

        this.sender = new Sender(config);

        this.suzukiRequestBuilder = new SuzukiRequestBuilder(config);
    }

    @Override
    public void handle(SuzukiRequest suzukiRequest) {
        SuzukiLogger.startEventHandling(suzukiRequest);


        int senderId = suzukiRequest.getSenderId();

        // max
        int s = suzukiRequest.getValue().getRequestNumber();
        RN.setNumber(
                senderId,
                Math.max(RN.numberOf(senderId), s)
        );

        // if thing
        if(suzukiToken != null) {  // not in CS cause its single threaded

            if(RN.numberOf(senderId) == suzukiToken.numberOf(senderId) + 1) {
                sender.send(senderId, suzukiToken);

                // TODO remove
                if(RN.numberOf(senderId) > suzukiToken.numberOf(senderId) + 1) {
                    throw new RuntimeException();
                }
                // TODO remove

                suzukiToken = null;
            }
        }
//        System.out.println("Handling suzukiRequest... done." + " RN: " + RN);
        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(SuzukiToken suzukiToken) {
        SuzukiLogger.startEventHandling(suzukiToken);

        this.suzukiToken = suzukiToken;

        runnableWithResource.run();
        runnableWithResource = null;

        suzukiToken.setNumber(config.getMyId(), RN.numberOf(myId));

        suzukiToken.appendProcesses(outstandingRequestProcesses(RN, suzukiToken));

        if(!suzukiToken.getValue().getQueue().isEmpty()) {
            int nodeId = suzukiToken.getValue().getQueue().get(0);
            suzukiToken.getValue().getQueue().remove(0);
            suzukiToken.setSenderId(config.getMyId());
            sender.send(nodeId, suzukiToken);
            this.suzukiToken = null;
        }

//        System.out.println("Handling suzukiToken... done." + " SuzukiToken: " + suzukiToken);
        SuzukiLogger.stopEventHandling();
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
        SuzukiLogger.startEventHandling(requestCS);

        this.runnableWithResource = requestCS.getRunnableWithResource();

        int myUpdatedNumber = RN.numberOf(myId) + 1;
        RN.setNumber(myId, myUpdatedNumber);
        sender.broadcast(config.getMyId(), suzukiRequestBuilder.build(myUpdatedNumber));

//        System.out.println("Handling requestCS... done." + " RN: " + RN);
        SuzukiLogger.stopEventHandling();
    }

    /**
     * @return outstanding request processes' nodeIds
     */
    private List<Integer> outstandingRequestProcesses(RN RN, SuzukiToken suzukiToken) {
        List<Integer> result = new LinkedList<>();


        // <nodeId, number>
        for (Map.Entry<Integer, Integer> entry : RN.entrySet()) {
            Integer nodeId = entry.getKey();
            Integer number = entry.getValue();

            if(number == suzukiToken.numberOf(nodeId) + 1) {
                result.add(nodeId);
            }

        }

        return result;
    }

}