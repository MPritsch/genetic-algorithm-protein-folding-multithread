package algorithm;

import lombok.Getter;
import lombok.Setter;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
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

    DefaultCategoryDataset lineChartDataset;

    public Population(int populationSize) {
        genepool = new ArrayList<>(populationSize);
        structures = new ArrayList<>(populationSize);

        lineChartDataset = new DefaultCategoryDataset();
    }

    public void addGensToGenpool(List<RelativeDirection> gens) {
        genepool.add(gens);
    }

    public void addStructure(Structure structure) {
        structures.add(structure);
    }

    public Structure printStatusAndGetBestStructure(int currentGeneration, Structure bestProtein) {
        totalFitness = 0;
        Structure currentBestProtein = structures.get(0);

        for (Structure structure : structures) {
            int fitness = structure.getFitness();
            totalFitness += fitness;
            if (fitness > currentBestProtein.getFitness()) {
                currentBestProtein = structure;
            }

        }

        float averageFitness = (float) totalFitness / (float) structures.size();

        if (currentBestProtein.getFitness() > 1) {
            if(currentBestProtein.getFitness() > bestProtein.getFitness()){
                bestProtein = currentBestProtein;
            }
            bestProtein.printStructure();
        } else {
            System.out.println("No valid structure was generated.");
        }

        System.out.println("Generation " + currentGeneration + ":");
        System.out.println("  Total Fitness: " + totalFitness);
        System.out.println("  Average Fitness " + averageFitness);
        System.out.println("  Best fitness in generation: " + currentBestProtein.getFitness());
        System.out.println("  Best overall fitness: " + bestProtein.getFitness());


//        lineChartDataset.addValue(totalFitness, "total fitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(averageFitness, "average fitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(bestProtein.getFitness(), "best overall fitness", String.valueOf(currentGeneration));
        lineChartDataset.addValue(currentBestProtein.getFitness(), "best fitness in generation", String.valueOf(currentGeneration));

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Genetic Algorithm flow", "Generation",
                "Fitness",
                lineChartDataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 1280;
        int height = 720;
        File lineChart = new File("GeneticAlgorithm.jpeg");

        try {
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
        } catch(Exception e){
            e.printStackTrace();
        }

        return currentBestProtein;
    }

    public void buildSelectionOnGenepool() {
        List<Pair> weightedStructures = buildWeightedStructures();

        List<Structure> selection =  pickWeightedStructuresRandomly(weightedStructures);

        genepool.clear();
        for (Structure structure : selection) {
            genepool.add(structure.getRelativeDirections());
        }
    }

    private List<Pair> buildWeightedStructures() {
        return structures.stream().map(i -> new Pair(i, (double) i.getFitness() /  (double) totalFitness)).collect(Collectors.toList());
    }

    private List<Structure> pickWeightedStructuresRandomly(List<Pair> weightedStructures) {
        Object[] randomSelection =  new EnumeratedDistribution(weightedStructures).sample(weightedStructures.size());

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
}
