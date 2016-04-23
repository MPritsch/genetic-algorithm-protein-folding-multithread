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

    private int neighborX;
    private int neighborY;

    private List<Node>[][] nodes;

    public HydrophobNeighborsCounter(List<Node>[][] nodes) {
        this.nodes = nodes;
    }

    public int countHydrophobNeighbors(Node startNode) {
        hydrophobNeighborCount = 0;
        Node currentNode = startNode;

        do {
            currentX = currentNode.getPosition().getX();
            currentY = currentNode.getPosition().getY();

            countNeighborsForCurrentPosition(currentNode);

            currentNode = currentNode.getNext();
        } while (currentNode != null);

        return hydrophobNeighborCount;
    }

    private void countNeighborsForCurrentPosition(Node currentNode) {
        countHorizontalNeighbors(currentNode);
        countVerticalNeighbors(currentNode);
    }

    private void countHorizontalNeighbors(Node currentNode) {
        neighborX = currentX + 1;
        neighborY = currentY;

        countNeighborsOnNeighborPosition(currentNode);

        neighborX = currentX - 1;
        countNeighborsOnNeighborPosition(currentNode);
    }

    private void countVerticalNeighbors(Node currentNode) {
        neighborX = currentX;
        neighborY = currentY - 1;
        countNeighborsOnNeighborPosition(currentNode);

        neighborY = currentY + 1;
        countNeighborsOnNeighborPosition(currentNode);
    }

    private void countNeighborsOnNeighborPosition(Node currentNode) {
        List<Node> neighborNodes = nodes[neighborX][neighborY];

        if (neighborNodes != null && !neighborNodes.isEmpty()) {
            for (Node neighborNode : neighborNodes) {
                if (areValidHydrophobNeighbors(currentNode, neighborNode)) {
                    hydrophobNeighborCount++;
                }
            }
        }
    }

    private boolean areValidHydrophobNeighbors(Node currentNode, Node neighborNode) {
        return currentNode.isHydrophob()
                && neighborNode != null
                && neighborNode.isHydrophob()
                && currentNode.getId() < neighborNode.getId();
    }
}
