package main;

import main.direction.relative.RelativeDirection;
import main.node.Node;

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
        Node[][] nodes = new FoldingStructureBuilder().buildStructure(primarySequence, relativeDirections);

        int fitness = new FoldingAnalyzer(nodes)
                .calculateTotalFitness();

        return fitness;
    }
}
