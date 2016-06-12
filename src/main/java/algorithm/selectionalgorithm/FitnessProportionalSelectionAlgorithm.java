package algorithm.selectionalgorithm;

import algorithm.Population;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 09.06.16.
 */
public class FitnessProportionalSelectionAlgorithm implements SelectionAlgorithm {

    public void selectOnPopulation(Population population) {
        List<Pair> weightedStructures = buildWeightedStructures(population.getStructures(), population.getTotalAbsoluteFitness());

        List<Structure> selection = pickWeightedStructuresRandomly(weightedStructures);

        List<List<RelativeDirection>> genepool = population.getGenepool();

        genepool.clear();
        for (Structure structure : selection) {
            genepool.add(structure.getRelativeDirections());
        }

        population.setGenepool(genepool);
    }

    private List<Pair> buildWeightedStructures(List<Structure> structures, double totalFitness) {
        return structures.stream().map(i -> new Pair(i, i.getAbsoluteFitness() / totalFitness)).collect(Collectors.toList());
    }

    private List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures) {
        Object[] randomSelection = new EnumeratedDistribution(weightedStructures).sample(weightedStructures.size());

        List<Structure> selection = Arrays.asList(Arrays.copyOf(randomSelection, randomSelection.length, Structure[].class));

        return selection;
    }
}
