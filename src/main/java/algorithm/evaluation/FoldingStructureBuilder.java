package algorithm.evaluation;

import algorithm.evaluation.direction.Move;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Node;
import algorithm.evaluation.node.Position;
import algorithm.evaluation.node.Structure;

import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingStructureBuilder {

    private String primarySequence;
    private List<RelativeDirection> relativeDirections;
    private Node[][] nodeStructure;

    private Structure structure;

    private Node startNode;
    private Node currentNode;

    public FoldingStructureBuilder(String primarySequence) {
        this.primarySequence = primarySequence;
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

    private Node[][] setupNodeArrays() {
        int arrayLength = primarySequence.length() + 1;
        return new Node[arrayLength][arrayLength];
    }

    private boolean hasValidInputs() {
        return primarySequence == null || primarySequence.length() == 0 || relativeDirections == null || relativeDirections.size() == 0
                || relativeDirections.size() != primarySequence.length() - 1;
    }

    private Structure buildNodeStructure() {
        if (!couldBuildStructure()) {
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

    private boolean couldBuildStructure() {
        Move move = setupStartNodeAndPrepareMoving();

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

            if (isOverlapping(nodeStructure[move.getX()][move.getY()])) {
                return false;
            }

            addNewNodeToNodeStructure(move);
        }
        return true;
    }

    private Move setupStartNodeAndPrepareMoving() {
        startNode = new Node(new Position((nodeStructure.length / 2) - 1, (nodeStructure.length / 2)));

        return prepareMoving();
    }

    private Move prepareMoving() {
        Move move = new Move(startNode.getPosition().getX(), startNode.getPosition().getY(), nodeStructure.length);

        nodeStructure[move.getX()][move.getY()] = startNode;
        currentNode = startNode;
        return move;
    }

    private boolean isOverlapping(Node node) {
        return node != null;
    }

    private void addNewNodeToNodeStructure(Move move) {
        currentNode = new Node(currentNode, new Position(move.getX(), move.getY()));
        nodeStructure[move.getX()][move.getY()] = currentNode;
    }

    private Structure returnOverlappingStructure() {
        structure.setOverlapping(true);
        return structure;
    }
}
