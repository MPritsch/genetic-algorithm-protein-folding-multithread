package algorithm.geneticalgorithm;

import algorithm.output.GraphicOutput;
import algorithm.Population;
import algorithm.PopulationGenerator;
import algorithm.selectionalgorithm.SelectionAlgorithm;
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

    protected boolean calculateHammingDistance;

    protected int currentGeneration;

    private GraphicOutput frame;
    private SelectionAlgorithm selectionAlgorithm;

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

    public GeneticAlgorithm calculatesHammingDistance(boolean hammingDistance) {
        this.calculateHammingDistance = hammingDistance;
        return this;
    }

    public GeneticAlgorithm usesSelectionAlgorithm(SelectionAlgorithm selectionAlgorithm){
        this.selectionAlgorithm = selectionAlgorithm;
        return this;
    }

    public Population generate() {
        setupStart();

        PopulationGenerator populationGenerator = new PopulationGenerator(primarySequence.length(), populationAmount);
        Population population = populationGenerator.generateStartPopulation();
        population.usesSelectionAlgorithm(selectionAlgorithm);

        population.evaluate(primarySequence);

        this.currentGeneration = 0;

        population.calculateHammingDistance(calculateHammingDistance);
        population.saveResults(currentGeneration);

        initializeFrame();
        paint(population);

        return generateTillLimit(population);
    }

    private void initializeFrame() {
        frame = new GraphicOutput();
    }

    private void paint(final Population population) {
        frame.setProtein(population.getBestProtein());
        frame.repaint();
    }

    protected void performAlgorithm(Population population) {
        population.select(); //selection
        population.crossover(crossoverRate);
        population.mutate(mutationRate);

        population.evaluate(primarySequence);

        population.calculateHammingDistance(calculateHammingDistance);
        population.saveResults(currentGeneration);

        paint(population);
    }

    protected abstract void setupStart();

    protected abstract Population generateTillLimit(Population population);
}
