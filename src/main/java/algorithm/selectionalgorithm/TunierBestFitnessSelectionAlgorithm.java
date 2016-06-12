package algorithm.selectionalgorithm;

import algorithm.Population;
import algorithm.evaluation.node.Structure;
import org.apache.commons.math3.util.Pair;

import java.time.Instant;
import java.util.*;

/**
 * Created by marcus on 12.06.16.
 */
public class TunierBestFitnessSelectionAlgorithm extends SelectionAlgorithm {

    private Random rand;
    private int candidateAmount;

    public TunierBestFitnessSelectionAlgorithm(int candidateAmount) {
        rand = new Random(Instant.now().toEpochMilli());

        this.candidateAmount = candidateAmount;
    }

    public void selectOnPopulation(Population population) {
        List<Structure> structures = population.getStructures();
        List<Structure> selectedStructures = new ArrayList<>(structures.size());

        for (int i = 0; i < structures.size(); i++) {
            Collections.shuffle(structures, rand);

            List<Structure> candidates = structures.subList(0, candidateAmount);

            selectedStructures.add(selectByBestFitness(candidates));
        }

        population.setStructures(selectedStructures);

        buildGenepoolOfSelection(population, selectedStructures);
    }

    private Structure selectByBestFitness(List<Structure> candidates) {
        Optional<Structure> pickedStructure = candidates.stream().max(Comparator.comparing(Structure::getAbsoluteFitness));

        return pickedStructure.orElse(null);
    }
}
