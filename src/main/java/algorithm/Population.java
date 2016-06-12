package algorithm;

import algorithm.crossover.CrossoverAlgorithm;
import algorithm.evaluation.FitnessCalculator;
import algorithm.evaluation.counter.HammingDistanceCounter;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;
import algorithm.mutation.MutationAlgorithm;
import algorithm.selectionalgorithm.SelectionAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 07.05.16.
 */
@Getter
@Setter
public class Population {

    private List<List<RelativeDirection>> genepool;

    private List<Structure> structures;
    private Structure bestProtein;

    private double totalAbsoluteFitness;
    private double averageAbsoluteFitness;
    private double totalRelativeFitness;
    private double averageRelativeFitness;
    private double averageNeighborCounter;
    private double averageOverlapCounter;
    private double totalHammingDistance;
    private double averageHammingDistance;

    private DefaultCategoryDataset lineChartDataset;
    private SelectionAlgorithm selectionAlgorithm;

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
        if (currentBestProtein.getAbsoluteFitness() > 0) {
            if (currentBestProtein.getAbsoluteFitness() > bestProtein.getAbsoluteFitness()) {
                bestProtein = currentBestProtein;
            }
        } else {
            System.out.println("No valid structure was generated.");
        }
    }

    private Structure calculateStatisticAndGetCurrentBestProtein() {
        totalAbsoluteFitness = 0;
        int totalNeighborCounter = 0;
        int totalOverlapCounter = 0;
        totalHammingDistance = 0;
        Structure currentBestProtein = structures.get(0);

        for (Structure structure : structures) {
            double fitness = structure.getAbsoluteFitness();
            totalAbsoluteFitness += fitness;
            totalNeighborCounter += structure.getValidNeighborCount();
            totalOverlapCounter += structure.getOverlappCounter();
            totalHammingDistance += structure.getAverageHammingDistance();
            if (fitness > currentBestProtein.getAbsoluteFitness()) {
                currentBestProtein = structure;
            }
        }

        averageAbsoluteFitness = totalAbsoluteFitness / (double) structures.size();
        averageNeighborCounter = (double) totalNeighborCounter / (double) structures.size();
        averageOverlapCounter = (double) totalOverlapCounter / (double) structures.size();
        averageHammingDistance = totalHammingDistance / (double) structures.size();

        return currentBestProtein;
    }

    private void saveAndPrintResults(int currentGeneration, Structure currentBestProtein) {
        printStatusOfCurrentGeneration(currentGeneration, currentBestProtein);
//        bestProtein.printStructure();
        saveValuesForChart(currentGeneration, currentBestProtein);
    }

    private void printStatusOfCurrentGeneration(int currentGeneration, Structure currentBestProtein) {
        System.out.println("Generation " + currentGeneration + ":");
        System.out.println("  Total Fitness: " + totalAbsoluteFitness);
        System.out.println("  Average Fitness " + averageAbsoluteFitness);
        System.out.println("  Average neighbor count: " + averageNeighborCounter);
        System.out.println("  Average overlap count: " + averageOverlapCounter);
        System.out.println("  Total Hamming distance: " + totalHammingDistance);
        System.out.println("  Average Hamming distance: " + averageHammingDistance);
        System.out.println("  Best overall: Fitness: " + bestProtein.getAbsoluteFitness());
        System.out.println("  Best overall: Overlaps " + bestProtein.getOverlappCounter());
//        System.out.println("  Best overall: Neighbor count: " + bestProtein.getNeighborCounter());
        System.out.println("  Best overall: Valid neighbor count: " + bestProtein.getValidNeighborCount());
        System.out.println("  Best in generation: absoluteFitness: " + currentBestProtein.getAbsoluteFitness());
        System.out.println("  Best in generation: Overlaps " + currentBestProtein.getOverlappCounter());
//        System.out.println("  Best in generation: Neighbor count: " + currentBestProtein.getNeighborCounter());
        System.out.println("  Best in generation: Valid neighbor count: " + currentBestProtein.getValidNeighborCount());
    }

    private void saveValuesForChart(int currentGeneration, Structure currentBestProtein) {
        //        lineChartDataset.addValue(totalAbsoluteFitness, "total absoluteFitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(averageAbsoluteFitness, "average absoluteFitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(bestProtein.getAbsoluteFitness(), "best overall absoluteFitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(currentBestProtein.getAbsoluteFitness(), "best absoluteFitness in generation", String.valueOf(currentGeneration));
        lineChartDataset.addValue(averageHammingDistance, "average hamming", String.valueOf(currentGeneration));
    }

    public void select() {
        selectionAlgorithm.selectOnPopulation(this);
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

    public List<Structure> calculateHammingDistance(boolean calcHammingDistance) {
        HammingDistanceCounter hammingDistanceCounter = new HammingDistanceCounter();
        structures = hammingDistanceCounter.calculateHammingDistance(calcHammingDistance, structures);
        return structures;
    }

    public void usesSelectionAlgorithm(SelectionAlgorithm selectionAlgorithm){
        this.selectionAlgorithm = selectionAlgorithm;
    }
}
