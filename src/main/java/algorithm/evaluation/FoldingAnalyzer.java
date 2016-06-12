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

    public double calculateTotalFitness(Structure structure) {
        if (structure.isValid()) {
            analyzeFolding(structure);

            int selectivePressure = 3;
//            double ratio = 0.6F;
            double bonus = ((double) 1 + (double) structure.getValidNeighborCount()); // * ratio;
            double malus = ((double) 1 + (double) structure.getOverlappCounter()); // * ((double) 1 - ratio);
            double expMalus = (double) Math.pow(malus, 3);

            double totalFitness = 0;
            totalFitness = bonus / expMalus;
            return Math.pow(totalFitness, selectivePressure);
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
        return new DirectHydrophobNeighborsCounter().countDirectNeighbors(structure.getStartNode(), structure.getNodes());
    }
}
