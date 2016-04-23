package main;

import main.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingStructureBuilder {

    public static List<Node>[][] buildStructure(String primarySequence, Node startNode) {
        if(isEmptySequenceOrStartNode(primarySequence, startNode)){
            return new ArrayList[0][0];
        }

        return buildNodeStructure(primarySequence, startNode);
    }

    private static boolean isEmptySequenceOrStartNode(String primarySequence, Node startNode) {
        return primarySequence == null || primarySequence.length() == 0 || startNode == null;
    }

    private static List<Node>[][] buildNodeStructure(String primarySequence, Node startNode) {
        List<Node>[][] nodeStructure = setupNodeArrays(primarySequence);

        Node currentNode = startNode;

        for (int charPosition = 0; charPosition < primarySequence.length(); charPosition++) {
            setHydrophobStatus(primarySequence, currentNode, charPosition);
            addNodeToStructure(nodeStructure, currentNode);

            currentNode = currentNode.getNext();
        }

        return nodeStructure;
    }

    private static List<Node>[][] setupNodeArrays(String primarySequence) {
        int arrayLength = primarySequence.length() * 2 + 1;
        return new ArrayList[arrayLength][arrayLength];
    }

    private static void setHydrophobStatus(String primarySequence, Node currentNode, int charPosition) {
        char hydrophobStatus = primarySequence.charAt(charPosition);

        currentNode.setHydrophob(hydrophobStatus);
    }

    private static void addNodeToStructure(List<Node>[][] nodeStructure, Node currentNode) {
        int currentX = currentNode.getPosition().getX();
        int currentY = currentNode.getPosition().getY();

        List<Node> nodeList = nodeStructure[currentX][currentY];
        if (nodeList == null) {
            nodeList = new ArrayList<>();
            currentNode.getPosition().setZ(0);
        }

        nodeList.add(currentNode);
        currentNode.getPosition().setZ(nodeList.size() - 1);

        nodeStructure[currentX][currentY] = nodeList;
    }
}
