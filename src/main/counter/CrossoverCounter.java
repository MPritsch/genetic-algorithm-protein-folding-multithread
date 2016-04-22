package main.counter;

import main.node.Node;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class CrossoverCounter {

    public static int countCrossovers(List<Node>[][] nodes){
        int crossoverCount = 0;
        for (int x = 0; x < nodes.length; x++) {
            List<Node>[] nodeRow = nodes[x];
            for (int y = 0; y < nodeRow.length; y++) {
                List<Node> nodesOnSinglePosition = nodeRow[y];
                if (nodesOnSinglePosition != null
                        && nodesOnSinglePosition.size() > 1) {
                    crossoverCount += nodesOnSinglePosition.size() - 1; //crossover amount rises with amount of crossovers
                }
            }
        }
        return crossoverCount;
    }
}
