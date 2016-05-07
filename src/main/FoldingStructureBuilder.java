package main;

import main.direction.Move;
import main.direction.RelativeDirection;
import main.node.Node;
import main.node.Position;
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

    public FoldingStructureBuilder(String primarySequence) {
        this.primarySequence = primarySequence;
    }

    private Node[][] setupNodeArrays() {
        int arrayLength = primarySequence.length() + 1;
        return new Node[arrayLength][arrayLength];
    }

    public Structure buildStructure(List<RelativeDirection> relativeDirections) {
        setupAttributes(relativeDirections);

        if (hasValidInputs()) {
            structure.setValid(false);
            return structure;
        }

        return buildNodeStructure();
    }

    private void setupAttributes(List<RelativeDirection> relativeDirections) {
        this.structure = new Structure();
        structure.setRelativeDirections(relativeDirections);

        this.relativeDirections = relativeDirections;
        this.nodeStructure = setupNodeArrays();
    }

    private boolean hasValidInputs() {
        return primarySequence == null || primarySequence.length() == 0 || relativeDirections == null || relativeDirections.size() == 0
                || relativeDirections.size() != primarySequence.length() - 1;
    }

    private Structure buildNodeStructure() {
        Node startNode = new Node(new Position((nodeStructure.length / 2) - 1, (nodeStructure.length / 2)));

        if (!couldBuildStructure(startNode)) {
            return returnOverlappingStructure();
        }

        addStatusToNodes(startNode);

        structure.setStartNode(startNode);
        structure.setNodes(nodeStructure);

        return structure;
    }

    private void addStatusToNodes(Node startNode) {
        Node currentNode = startNode;
        for (int i = 0; i < primarySequence.length(); i++) {
            char status = primarySequence.charAt(i);
            currentNode.setHydrophob(status);
            currentNode = currentNode.getNext();
        }
    }

    private boolean couldBuildStructure(Node startNode) {
        Node currentNode = startNode;

        Move move = new Move(currentNode.getPosition().getX(), currentNode.getPosition().getY(), nodeStructure.length);
        nodeStructure[move.getX()][move.getY()] = currentNode;

        for (int i = 0; i < primarySequence.length() - 1; i++) {
            switch (relativeDirections.get(i)) {
                case LEFT:
                    move.moveLeft();
                    break;
                case RIGHT:
                    move.moveRight();
                    break;
                case STRAIGHT:
                    move.moveStraight();
                    break;
            }

            if (nodeStructure[move.getX()][move.getY()] != null) {
                return false;
            }

            currentNode = new Node(currentNode, new Position(move.getX(), move.getY()));
            nodeStructure[move.getX()][move.getY()] = currentNode;
        }
        return true;
    }

    private Structure returnOverlappingStructure() {
        structure.setOverlapping(true);
        return structure;
    }
}
