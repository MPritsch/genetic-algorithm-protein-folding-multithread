package algorithm;

import algorithm.evaluation.FitnessCalculator;
import algorithm.evaluation.node.Structure;

import java.util.List;

/**
 * Created by marcus on 08.05.16.
 */
public class GeneticAlgorithm {

    private String primarySequence;

    private int generationAmount;
    private int populationAmount;

    private float mutationRate;
    private float crossoverRate;

    public GeneticAlgorithm() {
    }

    public GeneticAlgorithm usesPrimarySequence(String primarySequence) {
        this.primarySequence = primarySequence;
        return this;
    }

    public GeneticAlgorithm hasGenerations(int generationAmount) {
        this.generationAmount = generationAmount;
        return this;
    }

    public GeneticAlgorithm hasPopulationAmountOf(int populationAmount) {
        this.populationAmount = populationAmount;
        return this;
    }

    public GeneticAlgorithm hasMutationRateOf(float mutationRate) {
        this.mutationRate = mutationRate;
        return this;
    }

    public GeneticAlgorithm hasCrossoverRateOf(float crossoverRate) {
        this.crossoverRate = crossoverRate;
        return this;
    }

    public void generate() {
        PopulationGenerator populationGenerator = new PopulationGenerator(primarySequence.length(), populationAmount);
        Population population = populationGenerator.generateStartPopulation();


        FitnessCalculator fitnessCalculator = new FitnessCalculator(primarySequence);
        population = fitnessCalculator.calculateFitnessOfPopulation(population);


        int generation = 0;

        Structure bestProtein = population.printStatusAndGetBestStructure(generation);


        while (generation < generationAmount - 1) {
            generation++;

            //TODO selection
            List<Structure> selection = population.buildSelection(); //selection

            //TODO crossover
            //TODO mutation

            //TODO evaluation
            fitnessCalculator.calculateFitnessOfPopulation(population); //evaluation

            bestProtein = population.printStatusAndGetBestStructure(generation);
        }

    }
}
