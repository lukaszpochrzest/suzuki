package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.logging.SuzukiLogger;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventHandler;
import org.suzuki.algorithm.utils.SuzukiRequestBuilder;
import org.suzuki.communication.Sender;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.*;
import org.suzuki.data.internal.ElectionStart;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.election.ElectionManager;
import org.suzuki.util.DataGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SuzukiEventHandlerImpl implements SuzukiEventHandler, ElectionManager.ElectedListener {

    private SuzukiToken suzukiToken;

    private Suzuki.RunnableWithResource runnableWithResource;

    private RN RN;

    private int myId;

    private Config config;

    private SuzukiRequestBuilder suzukiRequestBuilder;

    private Sender sender;

    // TODO refactor election handling
    private ElectionManager electionManager;

    // hack until election algorithm is implemented TODO remove
    public SuzukiEventHandlerImpl(SuzukiToken suzukiToken) {
        this.suzukiToken = suzukiToken;
        initialize();
    }

    public SuzukiEventHandlerImpl() {
        initialize();
    }

    private void initialize() {
        this.config = ConfigHolder.getConfig();
        this.myId = config.getMyId();
        this.RN = new RN();

        this.sender = new Sender();

        this.suzukiRequestBuilder = new SuzukiRequestBuilder();

        this.electionManager = new ElectionManager(sender, this);
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

    // TODO refactor election handling
    @Override
    public void handle(ElectionBroadcast electionBroadcast) {
        SuzukiLogger.startEventHandling(electionBroadcast);

        electionManager.handle(electionBroadcast);

        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(ElectBroadcast electBroadcast) {
        SuzukiLogger.startEventHandling(electBroadcast);

        electionManager.handle(electBroadcast);

        SuzukiLogger.stopEventHandling();
    }

    // TODO refactor election handling
    @Override
    public void handle(ElectionOK electionOK) {
        //TODO logger for election
        SuzukiLogger.startEventHandling(electionOK);

        electionManager.handle(electionOK);

        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(RequestCS requestCS) {
        SuzukiLogger.startEventHandling(requestCS);

        if(suzukiToken != null) {
            requestCS.getRunnableWithResource().run();
            return;
        }

        this.runnableWithResource = requestCS.getRunnableWithResource();

        int myUpdatedNumber = RN.numberOf(myId) + 1;
        RN.setNumber(myId, myUpdatedNumber);
        sender.broadcast(config.getMyId(), suzukiRequestBuilder.build(myUpdatedNumber));

//        System.out.println("Handling requestCS... done." + " RN: " + RN);
        SuzukiLogger.stopEventHandling();
    }

    @Override
    public void handle(ElectionStart electionStart) {
        //TODO logger for election
        SuzukiLogger.startEventHandling(electionStart);

        electionManager.handle(electionStart);

        //  not necessary should iit here //TODO another mssage for this one
        electionManager.electionBroadcast();

        SuzukiLogger.stopEventHandling();
    }

    //TODO refactor
    @Override
    public void onElected() {

        //TODO logger for election
        SuzukiLogger.log("\nElected");
        electionManager.electBroadcast();
        suzukiToken = DataGenerator.generateInitialToken(config);
        SuzukiLogger.log("Generating token.\n");

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