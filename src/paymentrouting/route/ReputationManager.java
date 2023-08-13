
package paymentrouting.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import gtna.data.Single;
import gtna.graph.Edge;             
import gtna.graph.Edges;
import gtna.graph.Graph;
import gtna.graph.Node;

public class ReputationManager {
    // The data structure that holds timeouted nodes
    private List<Node> nodesOnTimeout;

    public ReputationManager() {
        this.nodesOnTimeout = new ArrayList<>();
    }

    // Method to decrease the reputation of a node and add it to nodesOnTimeout
    public void reduceReputationAndExclude(Node node) {
        double reputationDecrement = 0.25;

        if (node.getReputation() != 0.0)
            node.setReputation(node.getReputation() - reputationDecrement);

        // Calculate timeout based on the reputation score of the node
        long timeout = calculateTimeout(node.getReputation());

        // Set the node's exclusion time
        node.setExclusionTime(System.currentTimeMillis() + timeout);

        // Add the node to nodesOnTimeout
        nodesOnTimeout.add(node);
    }

    // Method to check all nodes in nodesOnTimeout and remove nodes with expired exclusion time
    public List<Node> checkExclusionTimeout() {
        List<Node> nodesToRemove = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for (Node node : nodesOnTimeout) {
            if (node.getExclusionTime() <= currentTime) {
                nodesToRemove.add(node);
            }
        }

        // Remove the nodes from nodesOnTimeout
        nodesOnTimeout.removeAll(nodesToRemove);

        return nodesToRemove;
    }

    // Helper method to calculate timeout based on reputation score
    private long calculateTimeout(double reputation) {
        
        long minTimeout = 60000; //60000 millisec equals 1 min
        return Math.round(minTimeout/(reputation + 0.1)); // + 0.1 to avoid dividing by zero
    }
}

	
