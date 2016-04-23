package main;

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

    public int calculateFitness(Node startNode) {
        List<Node>[][] nodes = new FoldingStructureBuilder().buildStructure(primarySequence, startNode);

        int fitness = new FoldingAnalyzer(nodes)
                .calculateTotalFitness(startNode);

        return fitness;
    }
}
