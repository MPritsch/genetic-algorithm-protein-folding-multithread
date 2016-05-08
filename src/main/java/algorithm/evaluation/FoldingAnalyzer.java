package algorithm.evaluation;

import algorithm.evaluation.counter.DirectHydrophobNeighborsCounter;
import algorithm.evaluation.counter.HydrophobNeighborsCounter;
import algorithm.evaluation.node.Structure;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingAnalyzer {

    private Integer neighborCount;
    private Integer directNeighborCount;

    public FoldingAnalyzer() {
        neighborCount = 0;
        directNeighborCount = 0;
    }

    public int calculateTotalFitness(Structure structure) {
        if (structure.isValidAndNotOverlappingStructure()) {
            analyzeFolding(structure);

            int totalFitness = 1 + (neighborCount - directNeighborCount);
            return totalFitness;
        }
        return 1;
    }

    private void analyzeFolding(Structure structure) {
        neighborCount = countNeighbors(structure);
        directNeighborCount = countDirectNeighbors(structure);
    }

    public int countNeighbors(Structure structure) {
        return new HydrophobNeighborsCounter(structure.getNodes()).countHydrophobNeighbors(structure.getStartNode());
    }

    public int countDirectNeighbors(Structure structure) {
        return new DirectHydrophobNeighborsCounter().countDirectNeighbors(structure.getStartNode());
    }
}
