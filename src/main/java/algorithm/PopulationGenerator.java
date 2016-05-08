package algorithm;

import algorithm.evaluation.direction.RelativeDirection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static algorithm.evaluation.direction.RelativeDirection.*;

/**
 * Created by marcus on 07.05.16.
 */
public class PopulationGenerator {

    private int directionsPerProtein;
    private int populationSize;
    private Population population;

    private Random rand;

    public PopulationGenerator(int sequenceSize, int populationSize) {
        this.directionsPerProtein = sequenceSize - 1;
        this.populationSize = populationSize;
        this.population = new Population(populationSize);

        this.rand = new Random(Instant.now().toEpochMilli());
    }

    public Population generateStartPopulation() {
        for (int i = 0; i < populationSize; i++) {
            List<RelativeDirection> gens = generateGensForSingleProtein();
            population.addGensToGenpool(gens);
        }

        return population;
    }

    private List<RelativeDirection> generateGensForSingleProtein() {
        List<RelativeDirection> protein = new ArrayList<>(directionsPerProtein);
        for (int i = 0; i < directionsPerProtein; i++) {
            protein.add(generateRandomDirection());
        }

        return protein;
    }

    private RelativeDirection generateRandomDirection() {
        int randomNumber = rand.nextInt(RelativeDirection.values().length);

        switch (randomNumber) {
            case 0:
                return LEFT;
            case 1:
                return RIGHT;
            case 2:
                return STRAIGHT;
        }

        return STRAIGHT;
    }
}
