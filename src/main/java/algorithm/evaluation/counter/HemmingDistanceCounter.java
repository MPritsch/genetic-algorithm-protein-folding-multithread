package algorithm.evaluation.counter;

import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;

import java.util.List;

/**
 * Created by marcus on 05.06.16.
 */
public class HemmingDistanceCounter {

    public List<Structure> calculateHemmingDistance(boolean calcHemmingDistance, List<Structure> structures) { //todo move/refactor
        if (calcHemmingDistance) {
            for (int i = 0; i < structures.size() - 1; i++) {
                Structure firstStructure = structures.get(i);

                for (int j = i + 1; j < structures.size(); j++) {
                    Structure secondStructure = structures.get(j);

                    int hemmingDistanceOfStructures = calculateHemmingDistanceForStrucuture(firstStructure, secondStructure);
                    firstStructure.addToTotalHemmingDistance(hemmingDistanceOfStructures);
                    secondStructure.addToTotalHemmingDistance(hemmingDistanceOfStructures);
                }

            }

            for (Structure structure : structures) {
                structure.calculateAverageHemmingDistance(structures.size());
            }
        }

        return structures;
    }

    private int calculateHemmingDistanceForStrucuture(Structure firstStructure, Structure secondStructure) {
        int hemmingDistance = 0;

        for (int i = 0; i < firstStructure.getRelativeDirections().size(); i++) {
            RelativeDirection firstDirection = firstStructure.getRelativeDirections().get(i);
            RelativeDirection secondDirection = secondStructure.getRelativeDirections().get(i);

            if (areDirectionsDifferent(firstDirection, secondDirection)) {
                hemmingDistance++;
            }
        }

        return hemmingDistance;
    }

    private boolean areDirectionsDifferent(RelativeDirection firstDirection, RelativeDirection secondDirection) {
        return !firstDirection.equals(secondDirection);
    }
}
