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
public abstract class SelectionAlgorithm {

    public abstract void selectOnPopulation(Population population);

    protected List<Pair> buildWeightedStructures(List<Structure> structures, double totalFitness) {
        return structures.stream().map(i -> new Pair(i, i.getAbsoluteFitness() / totalFitness)).collect(Collectors.toList());
    }

    protected List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures, int amount) {
        Object[] randomSelection = new EnumeratedDistribution(weightedStructures).sample(amount);

        List<Structure> selection = Arrays.asList(Arrays.copyOf(randomSelection, randomSelection.length, Structure[].class));

        return selection;
    }

    protected void buildGenepoolOfSelection(Population population, List<Structure> selection) {
        List<List<RelativeDirection>> genepool = population.getGenepool();

        genepool.clear();
        for (Structure structure : selection) {
            genepool.add(structure.getRelativeDirections());
        }

        population.setGenepool(genepool);
    }
}
