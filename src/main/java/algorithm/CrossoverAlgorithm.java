package algorithm;

import algorithm.evaluation.direction.RelativeDirection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by marcus on 16.05.16.
 */
public class CrossoverAlgorithm {

    private int crossoverTimes;
    private Random rand;

    private List<RelativeDirection> firstParent;
    private List<RelativeDirection> secondParent;

    public CrossoverAlgorithm(float crossoverRate, int populationAmount) {
        this.crossoverTimes = (Math.round(crossoverRate * populationAmount) + 1) / 2;

        this.rand = new Random(Instant.now().toEpochMilli());
    }

    public List<List<RelativeDirection>> crossoverGenepoolOfPopulation(List<List<RelativeDirection>> oldGenepool) {
        List<List<RelativeDirection>> crossoveredGenepool = new ArrayList<>();

        for (int i = 0; i < crossoverTimes; i++) {
            firstParent = getParentAndRemoveFromPool(oldGenepool);
            secondParent = getParentAndRemoveFromPool(oldGenepool);

            crossoveredGenepool = doOneCrossoverOnParents(crossoveredGenepool);
        }

        oldGenepool.addAll(crossoveredGenepool);

        return oldGenepool;
    }

    private List<RelativeDirection> getParentAndRemoveFromPool(List<List<RelativeDirection>> oldGenepool) {
        int oldGenepoolIndex = rand.nextInt(oldGenepool.size());
        return oldGenepool.remove(oldGenepoolIndex);
    }

    private List<List<RelativeDirection>> doOneCrossoverOnParents(List<List<RelativeDirection>> crossoveredGenepool) {
        int spliceIndex = rand.nextInt(firstParent.size() - 1) + 1;

        List<RelativeDirection> firstParentFirstHalf = firstParent.subList(0, spliceIndex);
        List<RelativeDirection> firstParentSecondHalf = firstParent.subList(spliceIndex, firstParent.size());
        List<RelativeDirection> secondParentFirstHalf = secondParent.subList(0, spliceIndex);
        List<RelativeDirection> secondParentSecondHalf = secondParent.subList(spliceIndex, secondParent.size());

        List<RelativeDirection> firstChild = buildChildFromParents(firstParentFirstHalf, secondParentSecondHalf);
        List<RelativeDirection> secondChild = buildChildFromParents(secondParentFirstHalf, firstParentSecondHalf);

        crossoveredGenepool.add(firstChild);
        crossoveredGenepool.add(secondChild);

        return crossoveredGenepool;
    }

    private List<RelativeDirection> buildChildFromParents(List<RelativeDirection> firstHalf, List<RelativeDirection> secondHalf)  {
        List<RelativeDirection> child = new ArrayList<>();
        child.addAll(firstHalf);
        child.addAll(secondHalf);

        return child;
    }
}
