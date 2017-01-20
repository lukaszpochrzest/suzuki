package org.suzuki.election;

import org.suzuki.algorithm.utils.ElectBroadcastBuilder;
import org.suzuki.communication.Sender;
import org.suzuki.config.Config;
import org.suzuki.config.ConfigHolder;
import org.suzuki.data.ElectBroadcast;
import org.suzuki.data.ElectionBroadcast;
import org.suzuki.data.ElectionBroadcastBody;
import org.suzuki.data.ElectionOK;
import org.suzuki.data.internal.ElectionStart;

public class ElectionManager {

    public interface ElectedListener {
        void onElected();
    }

    private Sender sender;

    private Config config;

    private ElectionNodeStateManager electionNodeStateManager;

    private ElectBroadcastBuilder electBroadcastBuilder;

    public ElectionManager(Sender sender, ElectedListener electedListener) {
        this.config = ConfigHolder.getConfig();
        this.sender = sender;
        this.electionNodeStateManager = new ElectionNodeStateManager(electedListener);
        this.electBroadcastBuilder = new ElectBroadcastBuilder();
    }

    public void electionBroadcast() {

        // TODO refactor
        ElectionBroadcastBody electionBroadcastBody = new ElectionBroadcastBody();
        electionBroadcastBody.setNodeId(config.getMyId());

        ElectionBroadcast electionBroadcast = new ElectionBroadcast();
        electionBroadcast.setSenderId(config.getMyId());
        electionBroadcast.setValue(electionBroadcastBody);

        sender.broadcast(config.getMyId(), electionBroadcast);

        electionNodeStateManager.onStartBullying();
    }

    public void electBroadcast() {
        sender.broadcast(config.getMyId(), electBroadcastBuilder.build());
    }


    public void handle(ElectionBroadcast electionBroadcast) {

        electionNodeStateManager.updateStateOn(electionBroadcast, config.getMyId());

        int senderId = electionBroadcast.getValue().getNodeId();

        if(config.getMyId() > senderId) {
            electionBroadcast();
        } else {
            //TODO refacor message building
            ElectionOK electionOK = new ElectionOK();
            electionOK.setSenderId(config.getMyId());

            sender.send(senderId, electionOK);
        }

    }

    public void handle(ElectionOK electionOK) {
        electionNodeStateManager.updateStateOn(electionOK);
    }

    public void handle(ElectionStart electionStart) {
        electionNodeStateManager.onStartElectionListening();
    }

    public void handle(ElectBroadcast electBroadcast) {
        electionNodeStateManager.updateStateOn(electBroadcast);
    }

}
