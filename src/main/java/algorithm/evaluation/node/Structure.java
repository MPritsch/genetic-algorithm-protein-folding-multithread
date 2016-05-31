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
    private float fitness = 0;

    private Node startNode;
    private Node[][] nodes;

    private List<RelativeDirection> relativeDirections;

    public void addToOverlappingCounter() {
        ++overlappCounter;
    }

    public int getValidNeighborCount() {
        return neighborCounter - directNeighborCounter;
    }

    public void printStructure() {
        if (nodes != null) {
            Node[][] rotatedNodes = rotate(this.nodes);

            System.out.println();

            for (int i = 0; i < rotatedNodes.length; i++) {
                for (int j = 0; j < rotatedNodes.length; j++) {
                    Node nodeEntry = rotatedNodes[i][j];
                    String status = "";
                    if (nodeEntry != null) {
                        status = nodeEntry.isHydrophob() ? "h" : "p";
                        System.out.print(nodeEntry.getId() + status + " ");
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
            }
        }
    }

    private Node[][] rotate(Node[][] nodes) {
        Node[][] newNodes = new Node[nodes.length][nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                newNodes[i][j] = nodes[j][i];
            }
        }
        return newNodes;
    }
}
