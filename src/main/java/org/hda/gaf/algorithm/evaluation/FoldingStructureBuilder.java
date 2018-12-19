package org.hda.gaf.algorithm.evaluation;

import org.hda.gaf.algorithm.evaluation.direction.Move;
import org.hda.gaf.algorithm.evaluation.direction.RelativeDirection;
import org.hda.gaf.algorithm.evaluation.node.Node;
import org.hda.gaf.algorithm.evaluation.node.Position;
import org.hda.gaf.algorithm.evaluation.node.Structure;

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
        buildStructure();

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

    private void buildStructure() {
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
                structure.addToOverlappingCounter();
            }

            addNewNodeToNodeStructure(move);
        }
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
        currentNode.setAbsolutePosition(new Position(move.getXAbsolute(), move.getYAbsolute()));
        nodeStructure[move.getX()][move.getY()] = currentNode;
    }
}
