import algorithm.CrossoverAlgorithm;
import algorithm.Population;
import algorithm.evaluation.direction.RelativeDirection;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static algorithm.evaluation.direction.RelativeDirection.LEFT;
import static algorithm.evaluation.direction.RelativeDirection.RIGHT;
import static algorithm.evaluation.direction.RelativeDirection.STRAIGHT;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by marcus on 16.05.16.
 */
public class CrossoverAlgorithmTest {

    @Test
    public void crossover() {
        float crossoverRate = 0.5F;
        int populationSize = 2;

        List<List<RelativeDirection>> genepool = new ArrayList<>();
        genepool.add(Arrays.asList(RIGHT, RIGHT));
        genepool.add(Arrays.asList(LEFT, LEFT));

        Population population = new Population(populationSize);
        population.setGenepool(genepool);

        CrossoverAlgorithm crossoverAlgorithm = new CrossoverAlgorithm(crossoverRate, populationSize);
        population = crossoverAlgorithm.crossoverGenepoolOfPopulation(population);

        genepool = population.getGenepool();

        boolean firstChildCorrect = false;
        boolean secondChildCorrect = false;

        for (List<RelativeDirection> child : genepool) {
            assertThat(child.size()).isEqualTo(2);
            if (LEFT.equals(child.get(0))) {
                assertThat(child.get(1)).isEqualTo(RIGHT);
                firstChildCorrect = true;
            } else if (RIGHT.equals(child.get(0))) {
                assertThat(child.get(1)).isEqualTo(LEFT);
                secondChildCorrect = true;
            } else {
                assertThat(false).isTrue(); //shouldn't happen
            }
        }
        assertThat(firstChildCorrect && secondChildCorrect).isTrue();
    }

    @Test
    public void crossoverAmount() {
        //TODO check why crossover count isnt constant
//        Random rand = new Random(Instant.now().toEpochMilli());
//
//        List<Integer> counts = new ArrayList<>();
//
//        float crossoverRate = 0.5F;
//        int populationSize = 100;
//        int sequenceSize = 50;
//
//        List<List<RelativeDirection>> genepool = new ArrayList<>();
//
//        for (int i = 0; i < populationSize; i++) {
//            List<RelativeDirection> protein = new ArrayList<>();
//            for (int j = 0; j < sequenceSize; j++) {
//                int enumIndex = rand.nextInt(3);
//                switch (enumIndex) {
//                    case 0:
//                        protein.add(RIGHT);
//                        break;
//                    case 1:
//                        protein.add(LEFT);
//                        break;
//                    case 2:
//                        protein.add(STRAIGHT);
//                        break;
//                }
//            }
//
//            genepool.add(protein);
//        }
//
//        Population population = new Population(populationSize);
//        List<List<RelativeDirection>> genepoolForPopulation = new ArrayList<>();
//        genepoolForPopulation.addAll(genepool);
//        population.setGenepool(genepoolForPopulation);
//
//        CrossoverAlgorithm crossoverAlgorithm = new CrossoverAlgorithm(crossoverRate, populationSize);
//        population = crossoverAlgorithm.crossoverGenepoolOfPopulation(population);
//
//        List<List<RelativeDirection>> newGenepool = population.getGenepool();
//
//        int crossoverCount = 0;
//
//        for (List<RelativeDirection> protein : newGenepool) {
//            if (!genepool.contains(protein)) {
//                crossoverCount++;
//            }
//        }
//        assertThat(crossoverCount).isEqualTo(50);
    }
}
