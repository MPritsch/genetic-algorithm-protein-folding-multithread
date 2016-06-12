package algorithm;

import algorithm.evaluation.node.Structure;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by marcus on 12.06.16.
 */
@Getter
@Setter
public class PopulationStatistic {

    private int generation;
    private double totalFitness;
    private double averageFitness;
    private double averageNeighborCounter;
    private double averageOverlapCounter;

    private double totalHammingDistance;
    private double averageHammingDistance;

    private Structure bestProtein;
    private Structure bestProteinInGeneration;
}
