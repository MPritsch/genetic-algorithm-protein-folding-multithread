package main;

import main.counter.OverlapCounter;
import main.counter.DirectNeighborCounter;
import main.counter.NeighborCounter;
import main.node.Node;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingAnalyzer {

    private List<Node>[][] nodes; //x, y, z (z for crossing)

    private Integer overlapCount;
    private Integer neighborCount;
    private Integer directNeighborCount;

    public FoldingAnalyzer(List<Node>[][] nodes) {
        this.nodes = nodes;

        overlapCount = 0;
        neighborCount = 0;
        directNeighborCount = 0;
    }

    public int calculateTotalFitness(Node startNode) {
        analyzeFolding(startNode);

        int totalFitness = neighborCount - overlapCount - directNeighborCount;

        return totalFitness;
    }

    private FoldingAnalyzer analyzeFolding(Node startNode) {
        overlapCount = countOverlaps();
        neighborCount = countNeighbors(startNode);
        directNeighborCount = countDirectNeighbors(startNode);

        return this;
    }

    public int countOverlaps() {
        return new OverlapCounter().countOverlaps(nodes);
    }

    public int countNeighbors(Node startNode) {
        return new NeighborCounter().countNeighbors();
    }

    public int countDirectNeighbors(Node startNode) {
        return new DirectNeighborCounter().countDirectNeighbors(startNode);
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
