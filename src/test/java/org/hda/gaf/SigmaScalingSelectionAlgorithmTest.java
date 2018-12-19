package org.hda.gaf;

import org.hda.gaf.algorithm.Population;
import org.hda.gaf.algorithm.evaluation.node.Structure;
import org.hda.gaf.algorithm.selectionalgorithm.SigmaScalingSelectionAlgorithm;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by marcus on 10.06.16.
 */
public class SigmaScalingSelectionAlgorithmTest {

    private SigmaScalingSelectionAlgorithm sigmaScalingSelectionAlgorithm;

    @Before
    public void setup() {
        sigmaScalingSelectionAlgorithm = new SigmaScalingSelectionAlgorithm();
    }

    @Test
    public void test() {
        Structure structure1 = new Structure();
        structure1.setAbsoluteFitness(1);
        Structure structure2 = new Structure();
        structure2.setAbsoluteFitness(2);
        Structure structure3 = new Structure();
        structure3.setAbsoluteFitness(3);
        Structure structure4 = new Structure();
        structure4.setAbsoluteFitness(7);

        Population population = new Population(4);
        population.setStructures(ImmutableList.of(structure1, structure2, structure3, structure4));

        sigmaScalingSelectionAlgorithm.selectOnPopulation(population);
    }
}
