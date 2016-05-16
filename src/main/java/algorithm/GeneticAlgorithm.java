package algorithm;

import algorithm.evaluation.FitnessCalculator;
import algorithm.evaluation.node.Structure;

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

        Structure bestProtein = population.printStatusAndGetBestStructure(generation, new Structure());

        while (generation < generationAmount - 1) {
            generation++;

            population.buildSelectionOnGenepool(); //selection
            population.crossover(crossoverRate);
            population.mutate(mutationRate);

            fitnessCalculator.calculateFitnessOfPopulation(population); //evaluation

            Structure generationsBestProtein = population.printStatusAndGetBestStructure(generation, bestProtein);
            if(generationsBestProtein.getFitness() > bestProtein.getFitness()){
                bestProtein = generationsBestProtein;
            }
        }
    }
}
