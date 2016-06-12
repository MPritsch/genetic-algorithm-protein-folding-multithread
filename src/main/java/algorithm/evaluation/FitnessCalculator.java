package algorithm.evaluation;

import algorithm.Population;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;

import java.util.ArrayList;
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
        population.setStructures(new ArrayList<>());

        for (List<RelativeDirection> gensOfSingleProtein : population.getGenepool()) {
            Structure structure = buildStructureWithFitness(gensOfSingleProtein);
            population.addStructure(structure);
        }
        return population;
    }

    private Structure buildStructureWithFitness(List<RelativeDirection> relativeDirections) {
        Structure structure = foldingStructureBuilder.buildStructure(relativeDirections);

        double fitness = foldingAnalyzer.calculateTotalFitness(structure);

        structure.setAbsoluteFitness(fitness);

        return structure;
    }
}
