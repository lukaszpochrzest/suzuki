package org.suzuki.algorithm;

import org.suzuki.Suzuki;
import org.suzuki.algorithm.communication.Sender;
import org.suzuki.algorithm.communication.tcp.server.TCPServer;
import org.suzuki.algorithm.queue.EventQueue;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventHandler;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventQueueListener;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigUtils;
import org.suzuki.data.*;
import org.suzuki.data.internal.RequestCS;
import org.suzuki.json.MessageParser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SuzukiAlgorithm {

    // util data TODO refactor

    private TCPServer tcpServer;

    //  algorithm data TODO refactor

    // TODO move objects that are not thread-safe to handler

    private int[] RN;

    private int myIndex;

    private Config config;

    private EventQueue eventQueue;

    private Sender sender;

    private RequestBuilder requestBuilder;

    // hack until election algorithm is implemented TODO remove
    public SuzukiAlgorithm(Config config, SuzukiToken suzukiToken) {
        this.eventQueue = new EventQueue(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl(suzukiToken)));
        initialize(config);
    }

    public SuzukiAlgorithm(Config config) {
        this.eventQueue = new EventQueue(new SuzukiEventQueueListener(new SuzukiEventHandlerImpl()));
        initialize(config);
    }

    private void initialize(Config config) {
        this.config = config;
        this.myIndex = indexOf(config.getMyId());
        this.RN = new int[config.getNodes().size()];

        this.sender = new Sender(config);

        this.requestBuilder = new RequestBuilder(config);
    }

    public void launch() {
        eventQueue.startListening();

        tcpServer = new TCPServer(
                config.getPort(),
                jsonMessage -> {
                    Message message = MessageParser.toObject(jsonMessage);
                    eventQueue.put(message);
                }
                );
        tcpServer.start();
    }

    public void executeLocked(Suzuki.RunnableWithResource runnableWithResource) {
        requestCS(runnableWithResource);
    }

    public void close() {
        tcpServer.close();
    }

    private void requestCS(Suzuki.RunnableWithResource runnableWithResource) {
        eventQueue.put(new RequestCS(runnableWithResource));
    }

    private class SuzukiEventHandlerImpl implements SuzukiEventHandler {

        private SuzukiToken suzukiToken;

        private Suzuki.RunnableWithResource runnableWithResource;

        // hack until election algorithm is implemented TODO remove
        public SuzukiEventHandlerImpl(SuzukiToken suzukiToken) {
            this.suzukiToken = suzukiToken;
        }

        public SuzukiEventHandlerImpl() {
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

                if(RN[processIndex] == TokenUtils.numberOf(suzukiRequest.getSenderId(), suzukiToken) + 1) {
                    sender.send(suzukiRequest.getSenderId(), suzukiToken);

                    // TODO remove
                    if(RN[processIndex] > TokenUtils.numberOf(suzukiRequest.getSenderId(), suzukiToken) + 1) {
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

            TokenUtils.setNumber(config.getMyId(), RN[myIndex], suzukiToken);

            TokenUtils.appendProcesses(outstandingRequestProcesses(RN, suzukiToken), suzukiToken);

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
            sender.broadcast(config.getMyId(), requestBuilder.build(++RN[myIndex]));

            System.out.println("Handling requestCS... done." + " RN: " + Arrays.toString(RN));
        }

        /**
         * @return outstanding request processes' nodeIds
         */
        private List<Integer> outstandingRequestProcesses(int[] RN, SuzukiToken suzukiToken) {
            List<Integer> result = new LinkedList<>();
            for(int k = 0 ; k < RN.length; ++k) {
                int nodeId = ConfigUtils.getNodeIdFor(k, config);
                int tokenNumber = TokenUtils.numberOf(nodeId, suzukiToken);
                if(RN[k] == tokenNumber + 1) {
                    result.add(nodeId);
                }
            }
            return result;
        }


    }

    private int indexOf(int nodeId) {
        return ConfigUtils.getIndexOf(nodeId, config);
    }

    private static class RequestBuilder {

        private Config config;


        public RequestBuilder(Config config) {
            this.config = config;
        }

        public SuzukiRequest build(int requestNumber) {
            SuzukiRequestBody value = new SuzukiRequestBody();
            value.setRequestNumber(requestNumber);

            SuzukiRequest suzukiRequest = new SuzukiRequest();
            suzukiRequest.setSenderId(config.getMyId());

            suzukiRequest.setValue(value);

            return suzukiRequest;
        }

    }

}
