package org.hda.gaf.algorithm.selectionalgorithm;

import org.hda.gaf.algorithm.Population;
import org.hda.gaf.algorithm.evaluation.node.Structure;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 09.06.16.
 */
public class SigmaScalingSelectionAlgorithm extends SelectionAlgorithm {

    private final double c = 2;

    public void selectOnPopulation(Population population) {

        double totalAbsoluteFitness = 0.0;

        List<Structure> structures = population.getStructures();

        for (Structure structure : structures) {
            totalAbsoluteFitness += structure.getAbsoluteFitness();
        }

        double averageAbsoluteFitness = totalAbsoluteFitness / (double) structures.size();

        population.getStatistic().setTotalFitness(totalAbsoluteFitness);
        population.getStatistic().setAverageFitness(averageAbsoluteFitness);

        double wert = 0.0;

        for (Structure structure : structures) {
            double in = structure.getAbsoluteFitness() - averageAbsoluteFitness;
            wert += Math.pow(in, 2);
        }

        double wert2 = wert / (double) structures.size();
        double variance = Math.sqrt(wert2);

        double standardDeviation = Math.sqrt(variance);

        double totalRelativeFitness = 0.0;

        //absoluteFitness function for each
//        for (Structure structure : structures) {
//            double absoluteFitness = structure.getAbsoluteFitness();
//
//
//            double wert3 = c * standardDeviation;
//            double wert4 = absoluteFitness - wert3;
//
//            if(wert4 > 0.00000000000001){
//                structure.setRelativeFitness(wert4);
//            } else{
//                structure.setRelativeFitness(0.00000000000001);
//            }
//
//            totalRelativeFitness += structure.getRelativeFitness();
//        }


        for (Structure structure : structures) {
            double fitness = structure.getAbsoluteFitness();

            double wert3 = fitness - averageAbsoluteFitness;
            double wert4 = c * standardDeviation;

            double wert5 = wert3 / wert4;

            double expVal = wert5 + 1;

            if(expVal > 0.000000000000001){
                structure.setRelativeFitness(expVal);
            } else{
                structure.setRelativeFitness(0.000000000000001);
            }

            totalRelativeFitness += structure.getRelativeFitness();
        }

        double averageRelativeFitness = totalRelativeFitness / (double) structures.size();

        population.setTotalRelativeFitness(totalRelativeFitness);
        population.setAverageRelativeFitness(averageRelativeFitness);

        selectFitnessProportional(population);
    }


    private void selectFitnessProportional(Population population) {
        List<Pair> weightedStructures = buildWeightedStructures(population.getStructures(), population.getTotalRelativeFitness());

        List<Structure> selection = pickWeightedStructuresRandomly(weightedStructures, weightedStructures.size());

        buildGenepoolOfSelection(population, selection);
    }

    @Override
    protected List<Pair> buildWeightedStructures(List<Structure> structures, double totalRelativeFitness) {
        return structures.stream().map(i -> new Pair(i, i.getRelativeFitness() / totalRelativeFitness)).collect(Collectors.toList());
    }

    @Override
    protected List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures, int amount) {
        Object[] randomSelection = new EnumeratedDistribution(weightedStructures).sample(amount);

        List<Structure> selection = Arrays.asList(Arrays.copyOf(randomSelection, randomSelection.length, Structure[].class));

        return selection;
    }
}
