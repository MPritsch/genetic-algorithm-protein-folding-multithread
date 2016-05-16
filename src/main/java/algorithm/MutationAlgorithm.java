package algorithm;

import algorithm.evaluation.direction.RelativeDirection;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static algorithm.evaluation.direction.RelativeDirection.*;

/**
 * Created by marcus on 16.05.16.
 */
public class MutationAlgorithm {

    private int mutationTimes;
    private int geneAmount;
    private int totalGeneAmount;

    private Random rand;

    public MutationAlgorithm() {
        rand = new Random();
    }

    public List<List<RelativeDirection>> mutate(List<List<RelativeDirection>> genepool, float mutationRate) {
        calculateMutationTimes(genepool, mutationRate);

        Set<Integer> genePositions = buildGenePositionsForMutation();

        for (Integer genePosition : genePositions) {
            int genepoolIndex = genePosition / geneAmount;
            int geneIndex = genePosition % geneAmount;

            List<RelativeDirection> previousProtein = genepool.get(genepoolIndex);
            RelativeDirection previousDirection = previousProtein.get(geneIndex);
            RelativeDirection nextDirection = STRAIGHT;
            boolean randomBool = rand.nextBoolean();

            //retrieve random different direction
            switch (previousDirection) {
                case RIGHT:
                    nextDirection = randomBool ? LEFT : STRAIGHT;
                    break;
                case LEFT:
                    nextDirection = randomBool ? RIGHT : STRAIGHT;
                    break;
                case STRAIGHT:
                    nextDirection = randomBool ? LEFT : RIGHT;
                    break;
            }

            previousProtein.set(geneIndex, nextDirection);
            genepool.set(genepoolIndex, previousProtein);
        }

        return genepool;
    }

    private Set<Integer> buildGenePositionsForMutation() {
        Set<Integer> genePositions = new HashSet<>();

        while (genePositions.size() != mutationTimes) {
            int index = rand.nextInt(totalGeneAmount);

            genePositions.add(index);
        }

        return genePositions;
    }

    private void calculateMutationTimes(List<List<RelativeDirection>> genepool, float mutationRate) {
        if (genepool.size() != 0) {
            geneAmount = genepool.get(0).size();
        }
        totalGeneAmount = genepool.size() * geneAmount;

        mutationTimes = Math.round((float) totalGeneAmount * mutationRate);
    }
}
