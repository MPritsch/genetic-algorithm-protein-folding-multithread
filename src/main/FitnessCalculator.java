package main;

import main.direction.relative.RelativeDirection;
import main.node.Node;
import main.node.Structure;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FitnessCalculator {

    private String primarySequence;

    public FitnessCalculator(String primarySequence) {
        this.primarySequence = primarySequence;
    }

    public int calculateFitness(List<RelativeDirection> relativeDirections) {
        Structure structure = new FoldingStructureBuilder(primarySequence).buildStructure(relativeDirections);

        int fitness = new FoldingAnalyzer(structure)
                .calculateTotalFitness();

        return fitness;
    }
}
