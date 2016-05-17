package algorithm;

import lombok.NoArgsConstructor;

/**
 * Created by marcus on 08.05.16.
 */
@NoArgsConstructor
public class GeneticAlgorithm {

    private String primarySequence;

    private int generationAmount;
    private int populationAmount;

    private float mutationRate;
    private float crossoverRate;

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

    public Population generate() {
        PopulationGenerator populationGenerator = new PopulationGenerator(primarySequence.length(), populationAmount);
        Population population = populationGenerator.generateStartPopulation();

        population.evaluate(primarySequence);

        int generation = 0;

        population.saveResults(generation);

        GraphicOutput frame = new GraphicOutput();
        frame.setProtein(population.getBestProtein());
        frame.repaint();

        while (generation < generationAmount - 1) {
            generation++;

            population.buildSelectionOnGenepool(); //selection
            population.crossover(crossoverRate);
            population.mutate(mutationRate);

            population.evaluate(primarySequence);

            population.saveResults(generation);

            frame.setProtein(population.getBestProtein());
            frame.repaint();
        }

        return population;
    }
}
