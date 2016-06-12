package algorithm.evaluation.node;

import algorithm.evaluation.direction.RelativeDirection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by marcus on 03.05.16.
 */
@Data
@NoArgsConstructor
public class Structure {

    private boolean valid = true;
    private int overlappCounter = 0;
    private int neighborCounter = 0;
    private int directNeighborCounter = 0;
    private double totalHammingDistance = 0;
    private double averageHammingDistance = 0;
    private double absoluteFitness = 0;
    private double relativeFitness = 0;

    private Node startNode;
    private Node[][] nodes;

    private List<RelativeDirection> relativeDirections;

    public void addToOverlappingCounter() {
        ++overlappCounter;
    }

    public int getValidNeighborCount() {
        return neighborCounter - directNeighborCounter;
    }

    public void addToTotalHammingDistance(int hammingDistanceAmount) {
        totalHammingDistance += hammingDistanceAmount;
    }

    public void calculateAverageHammingDistance(int populationAmount) {
        if (populationAmount > 1) {
            averageHammingDistance = totalHammingDistance / ((float) populationAmount - 1);
        }
    }
}
