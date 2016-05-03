package main;

import main.counter.DirectHydrophobNeighborsCounter;
import main.counter.HydrophobNeighborsCounter;
import main.node.Node;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingAnalyzer {

    private Node[][] nodes; //x, y, z (z for crossing)

    private Integer overlapCount;
    private Integer neighborCount;
    private Integer directNeighborCount;

    public FoldingAnalyzer(Node[][] nodes) {
        this.nodes = nodes;

        neighborCount = 0;
        directNeighborCount = 0;
    }

    public int calculateTotalFitness(Node startNode) {
        analyzeFolding(startNode);

        int totalFitness = neighborCount - directNeighborCount;

        return totalFitness;
    }

    private FoldingAnalyzer analyzeFolding(Node startNode) {
        neighborCount = countNeighbors(startNode);
        directNeighborCount = countDirectNeighbors(startNode);

        return this;
    }

    public int countNeighbors(Node startNode) {
        return new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);
    }

    public int countDirectNeighbors(Node startNode) {
        return new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode);
    }

//    public int getTotalEnergy(Position startPosition, Node[][] nodes) {
//        int substractEnergy = calculatePrimarySequenzEnergy();
//
//        int totalEnergy = -substractEnergy;
//
//        //calculate total energy
//        int currentX = startPosition.getX();
//        int currentY = startPosition.getY();
//        Node currentNode = nodes[currentX][currentY];
//
//        fillNodesWithStatus(currentNode);
//
//        do {
//            currentX = currentNode.getPosition().getX();
//            currentY = currentNode.getPosition().getY();
//
//            int neighborX = currentX++;
//            int neighborY = currentY++;
//            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);
//
//            neighborX = currentX--;
//            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);
//
//            neighborY = currentY--;
//            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);
//
//            neighborY = currentX++;
//            totalEnergy = addEnergyToTotalEnergy(nodes[neighborX][neighborY], totalEnergy, currentNode);
//
//        } while (currentNode.getNext() != null);
//
//        return totalEnergy;
//    }
//
//    private int addEnergyToTotalEnergy(Node neighborNode, int totalEnergy, Node currentNode) {
//        totalEnergy += getEnergyForCurrentAndNeighborNode(currentNode, neighborNode);
//        return totalEnergy;
//    }
//
//    private int getEnergyForCurrentAndNeighborNode(Node currentNode, Node neighborNode) {
//        //id muss kleiner als nachbar
//        if (neighborNode != null
//                && currentNode.isHydrophob()
//                && neighborNode.isHydrophob()
//                && currentNode.getId() < neighborNode.getId()) {
//            return 1;
//        }
//        return 0;
//    }
}
