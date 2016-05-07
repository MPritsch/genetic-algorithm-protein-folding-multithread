package main;

import main.counter.DirectHydrophobNeighborsCounter;
import main.counter.HydrophobNeighborsCounter;
import main.node.Node;
import main.node.Structure;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingAnalyzer {

    private Structure structure;
    private Node[][] nodes; //x, y, z (z for crossing)

    private Integer neighborCount;
    private Integer directNeighborCount;

    public FoldingAnalyzer(Structure structure) {
        this.structure = structure;
        this.nodes = structure.getNodes();

        neighborCount = 0;
        directNeighborCount = 0;
    }

    public int calculateTotalFitness() {
        analyzeFolding();

        int totalFitness = neighborCount - directNeighborCount;

        return totalFitness;
    }

    private FoldingAnalyzer analyzeFolding() {
        neighborCount = countNeighbors();
        directNeighborCount = countDirectNeighbors();

        return this;
    }

    public int countNeighbors() {
        return new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(structure.getStartNode());
    }

    public int countDirectNeighbors() {
        return new DirectHydrophobNeighborsCounter().countDirectNeighbors(structure.getStartNode());
    }
}
