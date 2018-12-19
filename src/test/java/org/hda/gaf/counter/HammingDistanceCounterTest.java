package org.hda.gaf.counter;

import org.hda.gaf.algorithm.evaluation.counter.HammingDistanceCounter;
import org.hda.gaf.algorithm.evaluation.direction.RelativeDirection;
import org.hda.gaf.algorithm.evaluation.node.Structure;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hda.gaf.algorithm.evaluation.direction.RelativeDirection.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created by marcus on 05.06.16.
 */
public class HammingDistanceCounterTest {

    private HammingDistanceCounter hammingDistanceCounter;

    private List<RelativeDirection> standardDirections = ImmutableList.of(LEFT, RIGHT, STRAIGHT, LEFT);
    private List<RelativeDirection> oneDifferenceDirections = ImmutableList.of(LEFT, RIGHT, STRAIGHT, STRAIGHT);
    private List<RelativeDirection> twoDifferenceDirections = ImmutableList.of(LEFT, RIGHT, LEFT, STRAIGHT);

    @Before
    public void setup() {
        hammingDistanceCounter = new HammingDistanceCounter();
    }

    @Test
    public void hemmingDistanceNotCalculatedFlag() {
        List<Structure> structures = ImmutableList.of(createStructure(standardDirections), createStructure(oneDifferenceDirections));

        structures = hammingDistanceCounter.calculateHammingDistance(false, structures); //calc flag = false

        for (Structure structure : structures) {
            assertEquals(0.0, structure.getAverageHammingDistance(), 0.0); //should not be calculated, standard value = 0
            assertEquals(0.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void sameDirections() {
        List<Structure> structures = ImmutableList.of(createStructure(standardDirections), createStructure(standardDirections));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            assertEquals(0.0, structure.getAverageHammingDistance(), 0.0);
            assertEquals(0.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void multipleSameDirections() {
        List<Structure> structures = ImmutableList.of(createStructure(standardDirections),
                createStructure(standardDirections),
                createStructure(standardDirections));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            assertEquals(0.0, structure.getAverageHammingDistance(), 0.0);
            assertEquals(0.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void oneDifferenceOnTwoDirections() {
        List<Structure> structures = ImmutableList.of(createStructure(standardDirections), createStructure(oneDifferenceDirections));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            assertEquals(1.0, structure.getAverageHammingDistance(), 0.0);
            assertEquals(1.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void twoDifferencesOnTwoDirections() {
        List<Structure> structures = ImmutableList.of(createStructure(standardDirections), createStructure(twoDifferenceDirections));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            assertEquals(2.0, structure.getAverageHammingDistance(), 0.0);
            assertEquals(2.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void oneDifferenceOnMultipleDirections() {
        List<RelativeDirection> directions1 = ImmutableList.of(STRAIGHT, LEFT);
        List<RelativeDirection> directions2 = ImmutableList.of(STRAIGHT, RIGHT);
        List<RelativeDirection> directions3 = ImmutableList.of(STRAIGHT, STRAIGHT);

        List<Structure> structures = ImmutableList.of(createStructure(directions1), createStructure(directions2), createStructure(directions3));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            assertEquals(1.0, structure.getAverageHammingDistance(), 0.0);
            assertEquals(2.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void twoDifferenceOnMultipleDirections() {
        List<RelativeDirection> directions1 = ImmutableList.of(STRAIGHT, LEFT);
        List<RelativeDirection> directions2 = ImmutableList.of(LEFT, RIGHT);
        List<RelativeDirection> directions3 = ImmutableList.of(RIGHT, STRAIGHT);

        List<Structure> structures = ImmutableList.of(createStructure(directions1), createStructure(directions2), createStructure(directions3));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            assertEquals(2.0, structure.getAverageHammingDistance(), 0.0);
            assertEquals(4.0, structure.getTotalHammingDistance(), 0.0);
        }
    }

    @Test
    public void multipleDifferencesOnMultipleDirections() {
        List<RelativeDirection> directions1 = ImmutableList.of(STRAIGHT, STRAIGHT, STRAIGHT, STRAIGHT);
        List<RelativeDirection> directions2 = ImmutableList.of(LEFT, LEFT, LEFT, LEFT);
        List<RelativeDirection> directions3 = ImmutableList.of(RIGHT, RIGHT, RIGHT, RIGHT);
        List<RelativeDirection> directions4 = ImmutableList.of(LEFT, RIGHT, STRAIGHT, RIGHT);

        List<Structure> structures = ImmutableList.of(createStructure(directions1), createStructure(directions2),
                createStructure(directions3), createStructure(directions4));

        structures = hammingDistanceCounter.calculateHammingDistance(true, structures);

        for (Structure structure : structures) {
            if (directions1.equals(structure.getRelativeDirections()) || directions2.equals(structure.getRelativeDirections())) {
                double expectedTotal = 4 + 4 + 3;
                double expectedAverage = expectedTotal / 3;

                assertEquals(expectedAverage, structure.getAverageHammingDistance(), 0.0);
                assertEquals(expectedTotal, structure.getTotalHammingDistance(), 0.0);
            }

            if (directions3.equals(structure.getRelativeDirections())) {
                double expectedTotal = 4 + 4 + 2;
                double expectedAverage = expectedTotal / 3;

                assertEquals(expectedAverage, structure.getAverageHammingDistance(), 0.0);
                assertEquals(expectedTotal, structure.getTotalHammingDistance(), 0.0);
            }

            if (directions4.equals(structure.getRelativeDirections())) {
                double expectedTotal = 3 + 3 + 2;
                double expectedAverage = expectedTotal / 3;

                assertEquals(expectedAverage, structure.getAverageHammingDistance(), 0.0);
                assertEquals(expectedTotal, structure.getTotalHammingDistance(), 0.0);
            }
        }

    }

    private Structure createStructure(List<RelativeDirection> directions) {
        Structure structure1 = new Structure();
        structure1.setRelativeDirections(directions);
        return structure1;
    }
}
