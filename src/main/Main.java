package main;

import main.evaluation.FitnessCalculator;
import main.evaluation.node.Structure;
import main.examples.Examples;

public class Main {

    public static void main(String[] args) {
	    String primarySequence = Examples.SEQ20;

        PopulationGenerator populationGenerator = new PopulationGenerator(primarySequence.length(), 100);
        Population population = populationGenerator.generateStartPopulation();


        FitnessCalculator fitnessCalculator = new FitnessCalculator(primarySequence);
        population = fitnessCalculator.calculateFitnessOfPopulation(population);

        int totalFitness = 0;
        Structure bestProtein = population.getStructures().get(0);

        for (Structure structure : population.getStructures()) {
            int fitness = structure.getFitness();
            totalFitness += fitness;
            if(fitness > bestProtein.getFitness()){
                bestProtein = structure;
            }

        }
        float averageFitness = (float) totalFitness / (float) population.getStructures().size();

        System.out.println("Generating population: " + primarySequence + " was successfull");
        System.out.println("Generation 1:");
        System.out.println("  Total Fitness: " + totalFitness);
        System.out.println("  Average Fitness " + averageFitness);
        System.out.println("  Best Protein with fitness: " + bestProtein.getFitness());
    }
}
