package org.hda.gaf.counter;


import org.hda.gaf.algorithm.evaluation.counter.DirectHydrophobNeighborsCounter;
import org.hda.gaf.algorithm.evaluation.node.Node;
import org.hda.gaf.algorithm.evaluation.node.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marcus on 19.04.16.
 */
public class DirectNeighborCounterTest {

    private Node[][] nodes;

    @Before
    public void setup() {
        nodes = new Node[3][3];
    }

    @Test
    public void emptyNodes() {
        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(null, new Node[0][0]);

        assertEquals(0, count);
    }

    @Test
    public void countZeroDirectNeighbors() {
        Node startNode = new Node(new Position(0, 0));
        nodes = putNodesInPosition(startNode);

        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode, nodes);

        assertEquals(0, count);
    }

    @Test
    public void countOneDirectNeighbor() {
        countOnTwoNeighbors('1', '1', 1);
    }

    @Test
    public void nonHydroNeighbors() {
        countOnTwoNeighbors('0', '0', 0);
    }

    @Test
    public void hydroAndNonHydroNeighbors() {
        countOnTwoNeighbors('0', '1', 0);
    }

    private void countOnTwoNeighbors(char status_1, char status_2, int expectedNeighbors) {
        Node startNode = new Node(new Position(0, 0));
        Node nextNode = new Node(startNode, new Position(0, 1));

        nodes = putNodesInPosition(startNode);

        startNode.setHydrophob(status_1);
        nextNode.setHydrophob(status_2);

        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode, nodes);

        assertEquals(expectedNeighbors, count);
    }

    @Test
    public void countTwoNeighbors() {
        countOnThreeNeighbors('1', '1', '1', 2);
    }

    @Test
    public void nonHydrophobInTheMiddle() {
        countOnThreeNeighbors('1', '0', '1', 0);
    }

    private void countOnThreeNeighbors(char status_1, char status_2, char status_3, int expectedNeighbors) {
        Node startNode = new Node(new Position(0, 0));
        Node nextNode_1 = new Node(startNode, new Position(0, 1));
        Node nextNode_2 = new Node(nextNode_1, new Position(1, 1));

        putNodesInPosition(startNode);

        startNode.setHydrophob(status_1);
        nextNode_1.setHydrophob(status_2);
        nextNode_2.setHydrophob(status_3);

        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode, nodes);

        assertEquals(expectedNeighbors, count);
    }

    @Test
    public void overriddenNodes() {
        Node startNode = new Node(new Position(0, 0));
        Node nextNode_1 = new Node(startNode, new Position(0, 1));
        Node nextNode_2 = new Node(nextNode_1, new Position(1, 1));
        Node nextNode_3 = new Node(nextNode_2, new Position(1, 0));
        Node nextNode_4 = new Node(nextNode_3, new Position(0, 0));

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('1');
        nextNode_2.setHydrophob('1');
        nextNode_3.setHydrophob('1');
        nextNode_4.setHydrophob('1');

        putNodesInPosition(startNode);

        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode, nodes);

        assertEquals(3, count); //3 instead of 4, because startnode gets overriden
    }

    private Node[][] putNodesInPosition(Node startNode) {
        Node node = startNode;

        nodes[node.getPosition().getX()][node.getPosition().getY()] = node;

        while (node.getNext() != null) {
            node = node.getNext();

            nodes[node.getPosition().getX()][node.getPosition().getY()] = node;
        }

        return nodes;
    }

}