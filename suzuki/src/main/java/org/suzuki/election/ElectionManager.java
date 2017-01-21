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
import org.suzuki.election.timeout.ElectionTimeouts;


//TODO suzuki handler and election manager cant both implement ElectedListneer. Merge ElectionManager and ElectionStateManager wisely
public class ElectionManager implements ElectedListener {

    //TODO refactor (create some Election-Suzuki each-other aware layer)
    public interface SuzukiTokenKeeper {

        boolean hasToken();

        void removeToken();

    }

    private Sender sender;

    private Config config;

    private ElectionNodeStateManager electionNodeStateManager;

    private ElectBroadcastBuilder electBroadcastBuilder;

    private ElectionTimeouts electionTimeouts;

    private ElectedListener electedListener;

    private SuzukiTokenKeeper suzukiTokenKeeper;

    public ElectionManager(Sender sender, ElectedListener electedListener, SuzukiTokenKeeper suzukiTokenKeeper) {
        this.config = ConfigHolder.getConfig();
        this.sender = sender;
        this.electionTimeouts = new ElectionTimeouts();
        //TODO wiser electedListner
        this.electionNodeStateManager = new ElectionNodeStateManager(this, electionTimeouts);
        this.electBroadcastBuilder = new ElectBroadcastBuilder();
        //TODO wiser electedListner
        this.electedListener = electedListener;
        this.suzukiTokenKeeper = suzukiTokenKeeper;
    }

    public void electionBroadcast() {

        // TODO refactor
        ElectionBroadcastBody electionBroadcastBody = new ElectionBroadcastBody();
        electionBroadcastBody.setNodeId(config.getMyId());

        ElectionBroadcast electionBroadcast = new ElectionBroadcast();
        electionBroadcast.setSenderId(config.getMyId());
        electionBroadcast.setValue(electionBroadcastBody);

        sender.broadcast(config.getMyId(), electionBroadcast);

        electionTimeouts.startElectionBroadcastTimeout();

        electionNodeStateManager.onStartBullying();
    }

    public void handle(ElectionBroadcast electionBroadcast) {

        if(suzukiTokenKeeper.hasToken()) {
            suzukiTokenKeeper.removeToken();
        }

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

    public void elected() {
        electionNodeStateManager.elected();
    }

    @Override
    public void onElected() {
        sender.broadcast(config.getMyId(), electBroadcastBuilder.build());
        electedListener.onElected();
    }

    //TODO remove this one
    public ElectionNodeState getElectionNodeState() {
        return electionNodeStateManager.getElectionNodeState();
    }

}
