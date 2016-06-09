package algorithm.evaluation.counter;

import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Structure;

import java.util.List;

/**
 * Created by marcus on 05.06.16.
 */
public class HammingDistanceCounter {

    public List<Structure> calculateHammingDistance(boolean calcHammingDistance, List<Structure> structures) { //todo move/refactor
        if (calcHammingDistance) {
            for (int i = 0; i < structures.size() - 1; i++) {
                Structure firstStructure = structures.get(i);

                for (int j = i + 1; j < structures.size(); j++) {
                    Structure secondStructure = structures.get(j);

                    int hammingDistanceOfStructures = calculateHammingDistanceForStructure(firstStructure, secondStructure);
                    firstStructure.addToTotalHammingDistance(hammingDistanceOfStructures);
                    secondStructure.addToTotalHammingDistance(hammingDistanceOfStructures);
                }

            }

            for (Structure structure : structures) {
                structure.calculateAverageHammingDistance(structures.size());
            }
        }

        return structures;
    }

    private int calculateHammingDistanceForStructure(Structure firstStructure, Structure secondStructure) {
        int hammingDistance = 0;

        for (int i = 0; i < firstStructure.getRelativeDirections().size(); i++) {
            RelativeDirection firstDirection = firstStructure.getRelativeDirections().get(i);
            RelativeDirection secondDirection = secondStructure.getRelativeDirections().get(i);

            if (areDirectionsDifferent(firstDirection, secondDirection)) {
                hammingDistance++;
            }
        }

        return hammingDistance;
    }

    private boolean areDirectionsDifferent(RelativeDirection firstDirection, RelativeDirection secondDirection) {
        return !firstDirection.equals(secondDirection);
    }
}
