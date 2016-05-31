package algorithm.geneticalgorithm;

import algorithm.GraphicOutput;
import algorithm.Population;

/**
 * Created by marcus on 31.05.16.
 */
public class GenerationLimitedAlgorithm extends GeneticAlgorithm{

    private Integer generationAmount;

    public GeneticAlgorithm usesGenerationLimit(int generationAmount) {
        this.generationAmount = generationAmount;
        return this;
    }

    @Override
    protected Population generateTillLimit(Population population, GraphicOutput frame){
        int generation = 0;

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

    @Override
    protected void setupStart() {

    }
}
