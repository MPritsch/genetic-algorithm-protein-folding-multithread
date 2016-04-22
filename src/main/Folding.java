package main;

import main.node.Node;
import main.node.Position;

/**
 * Created by marcus on 19.04.16.
 */
public class Folding {

    private static String primarySq;

    private Node[][] nodes;

    public Folding(String primarySq) {
        this.primarySq = primarySq;

        this.nodes = new Node[primarySq.length() * 2 + 1][primarySq.length() * 2 + 1]; //maximale ausbreitung 2 mal länge + 1
    }

    public int getTotalEnergy(Position startPosition, Node[][] nodes) {
        int substractEnergy = calculatePrimarySequenzEnergy();

        int totalEnergy = -substractEnergy;

        //calculate total energy
        int currentX = startPosition.getX();
        int currentY = startPosition.getY();
        Node currentNode = nodes[currentX][currentY];

        fillNodesWithStatus(currentNode);

        do {
            currentX = currentNode.getPosition().getX();
            currentY = currentNode.getPosition().getY();

            int neighborX = currentX++;
            int neighborY = currentY++;
            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);

            neighborX = currentX--;
            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);

            neighborY = currentY--;
            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);

            neighborY = currentX++;
            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);

        } while (currentNode.getNext() != null);

        return totalEnergy;
    }

    private int addEnergyToTotalEnergy(Node neighborNode, int totalEnergy, Node currentNode) {
        totalEnergy += getEnergyForCurrentAndNeighborNode(currentNode, neighborNode);
        return totalEnergy;
    }

    private int getEnergyForCurrentAndNeighborNode(Node currentNode, Node neighborNode) {
        //id muss kleiner als nachbar
        if (neighborNode != null
                && currentNode.isHydrophob()
                && neighborNode.isHydrophob()
                && currentNode.getId() < neighborNode.getId()) {
            return 1;
        }
        return 0;
    }

    private void fillNodesWithStatus(Node startNode) {
        Node node = startNode;

        for (int i = 0; i < primarySq.length(); i++) {
            Character status = primarySq.charAt(i);
            node.setHydrophob(status);

            node = node.getNext();
        }
    }

    public int calculatePrimarySequenzEnergy() {
        int energy = 0;

        Character previousAmino = null;

        for (int i = 0; i < primarySq.length(); i++) {
            Character amino = primarySq.charAt(i);

            if (previousAmino != null) {
                if (previousAmino.equals('1') && amino.equals('1')) {
                    energy++;
                }
            }
            previousAmino = amino;
        }

        return energy;
    }
}
