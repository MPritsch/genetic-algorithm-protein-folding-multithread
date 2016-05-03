package main.counter;

import main.node.Node;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class HydrophobNeighborsCounter {

    private int hydrophobNeighborCount;

    private int currentX;
    private int currentY;

    private Node[][] nodes;

    public HydrophobNeighborsCounter(Node[][] nodes) {
        this.nodes = nodes;
    }

    public int countHydrophobNeighbors(Node startNode) {
        hydrophobNeighborCount = 0;
        Node currentNode = startNode;

        do {
            setupCurrentPosition(currentNode);
            countNeighborsForCurrentPosition(currentNode);

            currentNode = currentNode.getNext();
        } while (currentNode != null);

        return hydrophobNeighborCount;
    }

    private void setupCurrentPosition(Node currentNode) {
        currentX = currentNode.getPosition().getX();
        currentY = currentNode.getPosition().getY();
    }

    private void countNeighborsForCurrentPosition(Node currentNode) {
        countHorizontalNeighbors(currentNode);
        countVerticalNeighbors(currentNode);
    }

    private void countHorizontalNeighbors(Node currentNode) {
        int neighborX = currentX + 1;
        int neighborY = currentY;

        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);

        neighborX = currentX - 1;
        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);
    }

    private void countVerticalNeighbors(Node currentNode) {
        int neighborX = currentX;
        int neighborY = currentY - 1;
        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);

        neighborY = currentY + 1;
        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);
    }

    private void countNeighborsOnNeighborPosition(Node currentNode, Node neighborNodes) {
        if (neighborNodesExist(neighborNodes)) {
            increaseCountForHydrophobNeighbors(currentNode, neighborNodes);
        }
    }

    private boolean neighborNodesExist(Node neighborNode) {
        return neighborNode != null;
    }

    private void increaseCountForHydrophobNeighbors(Node currentNode, Node neighborNode) {
            if (areValidHydrophobNeighbors(currentNode, neighborNode)) {
                hydrophobNeighborCount++;
            }
    }

    private boolean areValidHydrophobNeighbors(Node currentNode, Node neighborNode) {
        return currentNode.isHydrophob()
                && neighborNode != null
                && neighborNode.isHydrophob()
                && currentNode.getId() < neighborNode.getId();
    }
}
