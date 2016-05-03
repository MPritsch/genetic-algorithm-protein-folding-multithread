package main;

import main.direction.relative.RelativeDirection;
import main.node.Node;
import main.node.NodeBuilder;
import main.node.Structure;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingStructureBuilder {

    private String primarySequence;
    private List<RelativeDirection> relativeDirections;
    private Node[][] nodeStructure;

    private Structure structure;

    public FoldingStructureBuilder(String primarySequence, List<RelativeDirection> relativeDirections) {
        this.structure = new Structure();
        structure.setRelativeDirections(relativeDirections);

        this.relativeDirections = relativeDirections;
        this.primarySequence = primarySequence;
        this.nodeStructure = setupNodeArrays();
    }

    private Node[][] setupNodeArrays() {
        int arrayLength = primarySequence.length() + 1;
        return new Node[arrayLength][arrayLength];
    }

    public Structure buildStructure() {
        if(isEmptySequenceOrHasEmptyDirections()){
            structure.setValid(false);
        }

        return buildNodeStructure();
    }

    private boolean isEmptySequenceOrHasEmptyDirections() {
        return primarySequence == null || primarySequence.length() == 0 || relativeDirections == null || relativeDirections.size() == 0;
    }

    private Structure buildNodeStructure() {
        Node startNode = new NodeBuilder().createStartNode(relativeDirections.get(0));

        Node currentNode = startNode;

        for (int charPosition = 0; charPosition < primarySequence.length(); charPosition++) {
            setHydrophobStatus(currentNode, charPosition);
            if(!couldAddNodeToStructure(currentNode, relativeDirections.get(charPosition))){
                return structure; //abbruch
            }

            currentNode = currentNode.getNext();
        }

        structure.setNodes(nodeStructure);

        return structure;
    }

    private void setHydrophobStatus(Node currentNode, int charPosition) {
        char hydrophobStatus = primarySequence.charAt(charPosition);

        currentNode.setHydrophob(hydrophobStatus);
    }

    private boolean couldAddNodeToStructure(Node currentNode, RelativeDirection directionForNextNode) {
        Node followingNode = new NodeBuilder().createFollowingNode(currentNode, directionForNextNode);

        int x = followingNode.getPosition().getX();
        int y = followingNode.getPosition().getY();

        if(nodeStructure[x][y] == null){
            nodeStructure[x][y] = followingNode;
            return true;
        }

        structure.setOverlapping(true);
        return false;
    }
}
