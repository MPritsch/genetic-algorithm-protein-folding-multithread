import algorithm.CrossoverAlgorithm;
import algorithm.evaluation.direction.RelativeDirection;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static algorithm.evaluation.direction.RelativeDirection.LEFT;
import static algorithm.evaluation.direction.RelativeDirection.RIGHT;
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

        CrossoverAlgorithm crossoverAlgorithm = new CrossoverAlgorithm(crossoverRate, populationSize);
        genepool = crossoverAlgorithm.crossoverGenepoolOfPopulation(genepool);

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
        //Doesnt work 100%, because Lists are compared, not equaled...
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
//        List<List<RelativeDirection>> genepoolForPopulation = new ArrayList<>();
//        genepoolForPopulation.addAll(genepool);
//
//        CrossoverAlgorithm crossoverAlgorithm = new CrossoverAlgorithm(crossoverRate, populationSize);
//        List<List<RelativeDirection>> newGenepool = crossoverAlgorithm.crossoverGenepoolOfPopulation(genepool);
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

    @Test
    public void a () {
//        List<RelativeDirection> a = Arrays.asList(RIGHT, RIGHT);
//        List<RelativeDirection> b = Arrays.asList(RIGHT, RIGHT);
//
//        List<List<RelativeDirection>> c  = new ArrayList<>();
//        c.add(a);
//        c.add(b);
//
//        List<List<RelativeDirection>> d = c;
//        List<RelativeDirection> a1 = d.get(0);
//        List<RelativeDirection> b1 = d.get(1);
//
//        List<RelativeDirection> a2 = a1.subList(0, 1);
//        List<RelativeDirection> a3 = a1.subList(1,2);
//
//        List<RelativeDirection> b2 = b1.subList(0, 1);
//        List<RelativeDirection> b3 = b1.subList(1, 2);
//
//        List<RelativeDirection> a4 = new ArrayList<>();
//        List<RelativeDirection> b4 = new ArrayList<>();
//
//        a4.addAll(a2);
//        a4.addAll(b3);
//        b4.addAll(b2);
//        b4.addAll(a3);
//
//        d.clear();
//        d.add(a4);
//        d.add(b4);
//
//        assertThat(d.contains(c.get(0))).isTrue();
//        assertThat(d.contains(c.get(1))).isTrue();
    }
}
