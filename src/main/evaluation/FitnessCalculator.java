package main.evaluation;

import main.Population;
import main.evaluation.direction.RelativeDirection;
import main.evaluation.node.Structure;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FitnessCalculator {

    private FoldingStructureBuilder foldingStructureBuilder;
    private FoldingAnalyzer foldingAnalyzer;

    public FitnessCalculator(String primarySequence) {
        this.foldingStructureBuilder = new FoldingStructureBuilder(primarySequence);
        this.foldingAnalyzer = new FoldingAnalyzer();
    }

    public Population calculateFitnessOfPopulation(Population population) {
        for (List<RelativeDirection> gensOfSingleProtein : population.getGenpool()) {
            Structure structure = buildStructureWithFitness(gensOfSingleProtein);
            population.addStructure(structure);
        }
        return population;
    }

    private Structure buildStructureWithFitness(List<RelativeDirection> relativeDirections) {
        Structure structure = foldingStructureBuilder.buildStructure(relativeDirections);

        int fitness = foldingAnalyzer.calculateTotalFitness(structure);

        structure.setFitness(fitness);

        return structure;
    }
}
