package org.suzuki.algorithm;

import org.suzuki.data.SuzukiToken;
import org.suzuki.data.SuzukiTokenBodyElement;

import java.util.List;

// TODO move these functions to Token class
public class TokenUtils {

    public static int numberOf(int nodeId, SuzukiToken suzukiToken) {
        for(SuzukiTokenBodyElement element : suzukiToken.getValue().getLastRequests()) {
            if(nodeId == element.getNodeId()) {
                return element.getNumber();
            }
        }

        throw new RuntimeException("No entry found for " + nodeId + " in " + suzukiToken);
    }

    public static void setNumber(int nodeId, int number, SuzukiToken suzukiToken) {
        for(SuzukiTokenBodyElement element : suzukiToken.getValue().getLastRequests()) {
            if(nodeId == element.getNodeId()) {
                element.setNumber(number);
                return;
            }
        }

        throw new RuntimeException("No entry found for " + nodeId + " in " + suzukiToken);
    }

    public static void appendProcesses(List<Integer> nodeIds, SuzukiToken suzukiToken) {
        suzukiToken.getValue().getQueue().addAll(nodeIds);
    }

}
