package org.hda.gaf.algorithm.evaluation.counter;

import org.hda.gaf.algorithm.evaluation.node.Node;

/**
 * Created by marcus on 19.04.16.
 */
public class DirectHydrophobNeighborsCounter {

    public static int countDirectNeighbors(Node startNode, Node[][] nodes) {
        if (startNode == null) {
            return 0;
        }

        int directNeighborCount = 0;
        Node currentNode = startNode;

        do {
            Node directNeighborNode = currentNode.getNext();

            if (areDisplayedHydrophobNeighbors(nodes, currentNode, directNeighborNode)) {
                directNeighborCount++;
            }

            currentNode = currentNode.getNext();
        } while (currentNode != null);

        return directNeighborCount;
    }

    private static boolean areDisplayedHydrophobNeighbors(Node[][] nodes, Node currentNode, Node directNeighborNode) {
        return areHydrophobNeighbors(currentNode, directNeighborNode) && areDisplayed(nodes, currentNode, directNeighborNode);
    }

    //because of overriding in the nodes array, only the last on one position is displayed -> don't count underlying!
    private static boolean areDisplayed(Node[][] nodes, Node currentNode, Node directNeighborNode) {
        return isDisplayed(nodes, currentNode) && isDisplayed(nodes, directNeighborNode);
    }

    private static boolean isDisplayed(Node[][] nodes, Node node) {
        return node == nodes[node.getPosition().getX()][node.getPosition().getY()];
    }

    private static boolean areHydrophobNeighbors(Node currentNode, Node directNeighborNode) {
        return isHydrophobNode(currentNode) && isHydrophobNode(directNeighborNode);
    }

    private static boolean isHydrophobNode(Node node) {
        return node != null
                && node.isHydrophob();
    }
}
