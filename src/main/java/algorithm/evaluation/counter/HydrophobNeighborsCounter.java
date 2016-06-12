package algorithm.evaluation.counter;

import algorithm.evaluation.Torus;
import algorithm.evaluation.node.Node;

/**
 * Created by marcus on 19.04.16.
 */
public class HydrophobNeighborsCounter {

    private int hydrophobNeighborCount;

    private int currentX;
    private int currentY;

    private Node[][] nodes;

    private final Torus torus;

    public HydrophobNeighborsCounter(Node[][] nodes) {
        this.nodes = nodes;
        this.torus = new Torus(nodes.length);
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
        int neighborX = torus.convert(currentX + 1);
        int neighborY = torus.convert(currentY);

        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);

        neighborX = torus.convert(currentX - 1);
        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);
    }

    private void countVerticalNeighbors(Node currentNode) {
        int neighborX = torus.convert(currentX);
        int neighborY = torus.convert(currentY - 1);
        countNeighborsOnNeighborPosition(currentNode, nodes[neighborX][neighborY]);

        neighborY = torus.convert(currentY + 1);
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
