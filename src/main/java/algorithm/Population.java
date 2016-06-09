package algorithm;

import algorithm.evaluation.FitnessCalculator;
import algorithm.evaluation.counter.HemmingDistanceCounter;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.jfree.data.category.DefaultCategoryDataset;

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
    private Structure bestProtein;

    private float totalFitness;
    private float averageFitness;
    private float averageNeighborCounter;
    private float averageOverlapCounter;
    private float totalHemmingDistance;
    private float averageHemmingDistance;

    DefaultCategoryDataset lineChartDataset;

    public Population(int populationSize) {
        genepool = new ArrayList<>(populationSize);
        structures = new ArrayList<>(populationSize);
        bestProtein = new Structure();

        lineChartDataset = new DefaultCategoryDataset();
    }

    public void addGensToGenpool(List<RelativeDirection> gens) {
        genepool.add(gens);
    }

    public void addStructure(Structure structure) {
        structures.add(structure);
    }

    public void saveResults(int currentGeneration) {
        Structure currentBestProtein = calculateStatisticAndGetCurrentBestProtein();

        checkForNewBestProtein(currentBestProtein);
        saveAndPrintResults(currentGeneration, currentBestProtein);
    }

    private void checkForNewBestProtein(Structure currentBestProtein) {
        if (currentBestProtein.getFitness() > 0) {
            if (currentBestProtein.getFitness() > bestProtein.getFitness()) {
                bestProtein = currentBestProtein;
            }
        } else {
            System.out.println("No valid structure was generated.");
        }
    }

    private Structure calculateStatisticAndGetCurrentBestProtein() {
        totalFitness = 0;
        int totalNeighborCounter = 0;
        int totalOverlapCounter = 0;
        totalHemmingDistance = 0;
        Structure currentBestProtein = structures.get(0);

        for (Structure structure : structures) {
            float fitness = structure.getFitness();
            totalFitness += fitness;
            totalNeighborCounter += structure.getValidNeighborCount();
            totalOverlapCounter += structure.getOverlappCounter();
            totalHemmingDistance += structure.getAverageHemmingDistance();
            if (fitness > currentBestProtein.getFitness()) {
                currentBestProtein = structure;
            }
        }

        averageFitness = totalFitness / (float) structures.size();
        averageNeighborCounter = (float) totalNeighborCounter / (float) structures.size();
        averageOverlapCounter = (float) totalOverlapCounter / (float) structures.size();
        averageHemmingDistance = totalHemmingDistance / (float) structures.size();

        return currentBestProtein;
    }

    private void saveAndPrintResults(int currentGeneration, Structure currentBestProtein) {
        printStatusOfCurrentGeneration(currentGeneration, currentBestProtein);
//        bestProtein.printStructure();
        saveValuesForChart(currentGeneration, currentBestProtein);
    }

    private void printStatusOfCurrentGeneration(int currentGeneration, Structure currentBestProtein) {
        System.out.println("Generation " + currentGeneration + ":");
        System.out.println("  Total Fitness: " + totalFitness);
        System.out.println("  Average Fitness " + averageFitness);
        System.out.println("  Average neighbor count: " + averageNeighborCounter);
        System.out.println("  Average overlap count: " + averageOverlapCounter);
        System.out.println("  Total Hemming distance: " + totalHemmingDistance);
        System.out.println("  Average Hemming distance: " + averageHemmingDistance);
        System.out.println("  Best overall: Fitness: " + bestProtein.getFitness());
        System.out.println("  Best overall: Overlaps " + bestProtein.getOverlappCounter());
//        System.out.println("  Best overall: Neighbor count: " + bestProtein.getNeighborCounter());
        System.out.println("  Best overall: Valid neighbor count: " + bestProtein.getValidNeighborCount());
        System.out.println("  Best in generation: fitness: " + currentBestProtein.getFitness());
        System.out.println("  Best in generation: Overlaps " + currentBestProtein.getOverlappCounter());
//        System.out.println("  Best in generation: Neighbor count: " + currentBestProtein.getNeighborCounter());
        System.out.println("  Best in generation: Valid neighbor count: " + currentBestProtein.getValidNeighborCount());
    }

    private void saveValuesForChart(int currentGeneration, Structure currentBestProtein) {
        //        lineChartDataset.addValue(totalFitness, "total fitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(averageFitness, "average fitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(bestProtein.getFitness(), "best overall fitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(currentBestProtein.getFitness(), "best fitness in generation", String.valueOf(currentGeneration));
        lineChartDataset.addValue(averageHemmingDistance, "average hemming", String.valueOf(currentGeneration));
    }

    public void buildSelectionOnGenepool() {
        List<Pair> weightedStructures = buildWeightedStructures();

        List<Structure> selection = pickWeightedStructuresRandomly(weightedStructures);

        genepool.clear();
        for (Structure structure : selection) {
            genepool.add(structure.getRelativeDirections());
        }
    }

    private List<Pair> buildWeightedStructures() {
        return structures.stream().map(i -> new Pair(i, (double) i.getFitness() / (double) totalFitness)).collect(Collectors.toList());
    }

    private List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures) {
        Object[] randomSelection = new EnumeratedDistribution(weightedStructures).sample(weightedStructures.size());

        List<Structure> selection = Arrays.asList(Arrays.copyOf(randomSelection, randomSelection.length, Structure[].class));

        return selection;
    }

    public void crossover(float crossoverRate) {
        CrossoverAlgorithm crossoverAlgorithm = new CrossoverAlgorithm(crossoverRate, genepool.size());
        this.genepool = crossoverAlgorithm.crossoverGenepoolOfPopulation(genepool);
    }

    public void mutate(float mutationRate) {
        MutationAlgorithm mutationAlgorithm = new MutationAlgorithm();
        this.genepool = mutationAlgorithm.mutate(genepool, mutationRate);
    }

    public void evaluate(String primarySequence) {
        new FitnessCalculator(primarySequence).calculateFitnessOfPopulation(this);
    }

    public List<Structure> calculateHemmingDistance(boolean calcHemmingDistance) {
        HemmingDistanceCounter hemmingDistanceCounter = new HemmingDistanceCounter();
        structures = hemmingDistanceCounter.calculateHemmingDistance(calcHemmingDistance, structures);
        return structures;
    }
}
