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
        while (currentGeneration < generationAmount - 1) {
            performAlgorithm(population, frame);
        }

        return population;
    }

    @Override
    protected void setupStart() {

    }
}
