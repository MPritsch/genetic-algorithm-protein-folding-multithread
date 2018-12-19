package org.hda.gaf.algorithm.selectionalgorithm;

import org.hda.gaf.algorithm.Population;
import org.hda.gaf.algorithm.evaluation.node.Structure;
import org.apache.commons.math3.util.Pair;

import java.time.Instant;
import java.util.*;

/**
 * Created by marcus on 12.06.16.
 */
public class TunierFitnessProportionalSelectionAlgorithm extends SelectionAlgorithm {

    private Random rand;
    private int candidateAmount;

    public TunierFitnessProportionalSelectionAlgorithm(int candidateAmount) {
        rand = new Random(Instant.now().toEpochMilli());

        this.candidateAmount = candidateAmount;
    }

    public void selectOnPopulation(Population population) {
        List<Structure> structures = population.getStructures();
        List<Structure> selectedStructures = new ArrayList<>(structures.size());

        for (int i = 0; i < structures.size(); i++) {
            Collections.shuffle(structures, rand);

            List<Structure> candidates = structures.subList(0, candidateAmount);

            double totalFitnessOfCandidates = candidates.stream().mapToDouble(Structure::getAbsoluteFitness).sum();

            selectedStructures.add(selectWeightedRandom(candidates, totalFitnessOfCandidates));
        }

        population.setStructures(selectedStructures);

        buildGenepoolOfSelection(population, selectedStructures);
    }

    private Structure selectWeightedRandom(List<Structure> candidates, double totalFitnessOfCandidates) {
        List<Pair> weightedCandidates = buildWeightedStructures(candidates, totalFitnessOfCandidates);

        List<Structure> pickedStructure = pickWeightedStructuresRandomly(weightedCandidates, 1);//select one

        return pickedStructure.get(0);
    }
}
