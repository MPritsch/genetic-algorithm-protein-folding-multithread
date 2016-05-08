package main;

import main.examples.Examples;

public class Main {

    final static int GENERATION_AMOUNT = 10;
    final static int POPULATION_AMOUNT = 200;
    final static float MUTATION_RATE = 0.05F;
    final static float CROSSOVER_RATE = 0.25F;

    final static String PRIMARY_SEQUENCE = Examples.SEQ20;

    public static void main(String[] args) {
        new GeneticAlgorithm()
                .usesPrimarySequence(PRIMARY_SEQUENCE)
                .hasGenerations(GENERATION_AMOUNT)
                .hasPopulationAmountOf(POPULATION_AMOUNT)
                .hasCrossoverRateOf(CROSSOVER_RATE)
                .hasMutationRateOf(MUTATION_RATE)
                .generate();
    }
}
