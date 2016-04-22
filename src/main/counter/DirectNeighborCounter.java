package main.counter;

import main.node.Node;

/**
 * Created by marcus on 19.04.16.
 */
public class DirectNeighborCounter {

    public static int countDirectNeighbors(Node startNode) {
        int directNeighborCount = 0;
        Node currentNode = startNode;

        do {
            Node directNeighborNode = currentNode.getNext();
            if (isNodeHydrophob(currentNode) && isNodeHydrophob(directNeighborNode)) {
                directNeighborCount++;
            }
            currentNode = currentNode.getNext();
        } while (currentNode != null);

        return directNeighborCount;
    }

    private static boolean isNodeHydrophob(Node node) {
        return node != null
                && node.isHydrophob();
    }
}
