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
public class SigmaScalingSelectionAlgorithm implements SelectionAlgorithm {

    private final double c = 2;

    public void selectOnPopulation(Population population) {
        //calc avrg
        //calc varianz
        //calc sigma/standardabweichung

        double totalAbsoluteFitness = 0.0;

        List<Structure> structures = population.getStructures();

        for (Structure structure : structures) {
//            structure.setAbsoluteFitness(structure.getAbsoluteFitness()+1);
            totalAbsoluteFitness += structure.getAbsoluteFitness();
        }

        double averageAbsoluteFitness = totalAbsoluteFitness / (double) structures.size();

        population.setTotalAbsoluteFitness(totalAbsoluteFitness);
        population.setAverageAbsoluteFitness(averageAbsoluteFitness);

        double wert = 0.0;

        for (Structure structure : structures) {
            double in = structure.getAbsoluteFitness() - averageAbsoluteFitness;
            wert += Math.pow(in, 2);
        }

        double wert2 = wert / (double) structures.size();
        double variance = Math.sqrt(wert2);

        double standardDeviation = Math.sqrt(variance);


        //absoluteFitness function for each
//        for (Structure structure : structures) {
//            double absoluteFitness = structure.getAbsoluteFitness();
//
//
//
//            double wert3 = c * standardDeviation;
//            double wert4 = absoluteFitness - wert3;
//
//            if(wert4 > 0){
//                structure.setAbsoluteFitness(wert4);
//            } else{
//                structure.setAbsoluteFitness(0.00001);
//            }
//        }


        double totalRelativeFitness = 0.0;

        for (Structure structure : structures) {
            double fitness = structure.getAbsoluteFitness();

            double wert3 = fitness - averageAbsoluteFitness;
            double wert4 = c * standardDeviation;

            double wert5 = wert3 / wert4;

            double expVal = wert5 + 1;

            if(expVal > 0.1){
                structure.setRelativeFitness(expVal);
            } else{
                structure.setRelativeFitness(0.0001);
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

        List<Structure> selection = pickWeightedStructuresRandomly(weightedStructures);

        List<List<RelativeDirection>> genepool = population.getGenepool();

        genepool.clear();
        for (Structure structure : selection) {
            genepool.add(structure.getRelativeDirections());
        }

        population.setGenepool(genepool);
    }

    private List<Pair> buildWeightedStructures(List<Structure> structures, double totalRelativeFitness) {
        return structures.stream().map(i -> new Pair(i, i.getRelativeFitness() / totalRelativeFitness)).collect(Collectors.toList());
    }

    private List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures) {
        Object[] randomSelection = new EnumeratedDistribution(weightedStructures).sample(weightedStructures.size());

        List<Structure> selection = Arrays.asList(Arrays.copyOf(randomSelection, randomSelection.length, Structure[].class));

        return selection;
    }
}
