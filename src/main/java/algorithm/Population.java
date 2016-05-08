package algorithm;

import lombok.Getter;
import lombok.Setter;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 07.05.16.
 */
@Getter
@Setter
public class Population {

    private List<List<RelativeDirection>> genepool;

    private List<Structure> structures;

    private int totalFitness;

    public Population(int populationSize) {
        genepool = new ArrayList<>(populationSize);
        structures = new ArrayList<>(populationSize);
    }

    public void addGensToGenpool(List<RelativeDirection> gens) {
        genepool.add(gens);
    }

    public void addStructure(Structure structure) {
        structures.add(structure);
    }

    public Structure printStatusAndGetBestStructure(int currentGeneration) {
        totalFitness = 0;
        Structure bestProtein = structures.get(0);

        for (Structure structure : structures) {
            int fitness = structure.getFitness();
            totalFitness += fitness;
            if (fitness > bestProtein.getFitness()) {
                bestProtein = structure;
            }

        }

        float averageFitness = (float) totalFitness / (float) structures.size();

        System.out.println("Generation " + currentGeneration + ":");
        System.out.println("  Total Fitness: " + totalFitness);
        System.out.println("  Average Fitness " + averageFitness);
        System.out.println("  Best Protein with fitness: " + bestProtein.getFitness());

        if (bestProtein.getFitness() > 1) {
            bestProtein.printStructure();
        } else {
            System.out.println("No valid structure was generated.");
        }

        return bestProtein;
    }

    public List<Structure> buildSelection() {
        List<Pair> weightedStructures = buildWeightedStructures();

        return pickWeightedStructuresRandomly(weightedStructures);
    }

    private List<Pair> buildWeightedStructures() {
        return structures.stream().map(i -> new Pair(i, (double) i.getFitness() /  (double) totalFitness)).collect(Collectors.toList());
    }

    private List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures) {
        Object[] randomSelection =  new EnumeratedDistribution(weightedStructures).sample(weightedStructures.size());

        List<Structure> selection = Arrays.asList(Arrays.copyOf(randomSelection, randomSelection.length, Structure[].class));

        return selection;
    }
}
