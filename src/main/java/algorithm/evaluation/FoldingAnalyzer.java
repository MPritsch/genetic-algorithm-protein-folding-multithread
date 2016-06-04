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

    public float calculateTotalFitness(Structure structure) {
        if (structure.isValid()) {
            analyzeFolding(structure);

            float ratio = 0.6F;
            float bonus = ((float) 1 + (float) structure.getValidNeighborCount()) * ratio;
            float malus = ((float) 1 + (float) structure.getOverlappCounter()) * ((float) 1 - ratio);
            float expMalus = (float) Math.pow(malus, 3);

            float totalFitness = 0;
            totalFitness = bonus / expMalus;
            return totalFitness;
        }
        return 0F;
    }

    private void analyzeFolding(Structure structure) {
        neighborCount = countNeighbors(structure);
        directNeighborCount = countDirectNeighbors(structure);

        structure.setNeighborCounter(neighborCount);
        structure.setDirectNeighborCounter(directNeighborCount);
    }

    public int countNeighbors(Structure structure) {
        return new HydrophobNeighborsCounter(structure.getNodes()).countHydrophobNeighbors(structure.getStartNode());
    }

    public int countDirectNeighbors(Structure structure) {
        return new DirectHydrophobNeighborsCounter().countDirectNeighbors(structure.getStartNode());
    }
}
