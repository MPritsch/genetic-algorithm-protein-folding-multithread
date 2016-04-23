package main.counter;

import main.node.Node;

/**
 * Created by marcus on 19.04.16.
 */
public class DirectHydrophobNeighborsCounter {

    public static int countDirectNeighbors(Node startNode) {
        int directNeighborCount = 0;
        Node currentNode = startNode;

        do {
            Node directNeighborNode = currentNode.getNext();

            if (areHydrophobNeighbors(currentNode, directNeighborNode)) {
                directNeighborCount++;
            }

            currentNode = currentNode.getNext();
        } while (currentNode != null);

        return directNeighborCount;
    }

    private static boolean areHydrophobNeighbors(Node currentNode, Node directNeighborNode) {
        return isHydrophobNode(currentNode) && isHydrophobNode(directNeighborNode);
    }

    private static boolean isHydrophobNode(Node node) {
        return node != null
                && node.isHydrophob();
    }
}
