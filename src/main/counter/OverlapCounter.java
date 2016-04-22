package main.counter;

import main.node.Node;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class OverlapCounter {

    public static int countOverlaps(List<Node>[][] nodes) {
        int overlapCount = 0;

        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[x].length; y++) {
                overlapCount += countOverlapsOnSinglePosition(nodes[x][y]);
            }
        }

        return overlapCount;
    }

    private static int countOverlapsOnSinglePosition(List<Node> nodesOnSinglePosition) {
        return nodesOnSinglePosition.size() - 1;
    }
}
