package org.suzuki.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.suzuki.algorithm.queue.suzuki.SuzukiEventVisitor;

import java.util.List;

@ToString
public class SuzukiToken extends Message {

    @Getter
    @Setter
    private SuzukiTokenBody value;

    public SuzukiToken() {
        type = TYPE_TOKEN;
    }

    @Override
    public void accept(SuzukiEventVisitor suzukiEventVisitor) {
        suzukiEventVisitor.visit(this);
    }

    public int numberOf(int nodeId) {
        for(SuzukiTokenBodyElement element : getValue().getLastRequests()) {
            if(nodeId == element.getNodeId()) {
                return element.getNumber();
            }
        }

        throw new RuntimeException("No entry found for " + nodeId + " in " + this);
    }

    public void setNumber(int nodeId, int number) {
        for(SuzukiTokenBodyElement element : getValue().getLastRequests()) {
            if(nodeId == element.getNodeId()) {
                element.setNumber(number);
                return;
            }
        }

        throw new RuntimeException("No entry found for " + nodeId + " in " + this);
    }

    public void appendProcesses(List<Integer> nodeIds) {
        getValue().getQueue().addAll(nodeIds);
    }

}
