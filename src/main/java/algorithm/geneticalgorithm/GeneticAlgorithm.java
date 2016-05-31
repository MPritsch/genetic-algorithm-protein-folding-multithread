package algorithm.geneticalgorithm;

import algorithm.GraphicOutput;
import algorithm.Population;
import algorithm.PopulationGenerator;
import lombok.NoArgsConstructor;

/**
 * Created by marcus on 08.05.16.
 */
@NoArgsConstructor
public abstract class GeneticAlgorithm {

    protected String primarySequence;

    protected int populationAmount;

    protected float mutationRate;
    protected float crossoverRate;

    public GeneticAlgorithm usesPrimarySequence(String primarySequence) {
        this.primarySequence = primarySequence;
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
        setupStart();

        PopulationGenerator populationGenerator = new PopulationGenerator(primarySequence.length(), populationAmount);
        Population population = populationGenerator.generateStartPopulation();

        population.evaluate(primarySequence);

        int generation = 0;

        population.saveResults(generation);

        GraphicOutput frame = new GraphicOutput();
        frame.setProtein(population.getBestProtein());
        frame.repaint();

        return generateTillLimit(population, frame);
    }

    protected abstract void setupStart();

    protected abstract Population generateTillLimit(Population population, GraphicOutput frame);
}
