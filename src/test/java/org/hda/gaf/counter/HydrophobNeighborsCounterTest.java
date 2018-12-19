package org.hda.gaf.counter;

import org.hda.gaf.algorithm.evaluation.counter.HydrophobNeighborsCounter;
import org.hda.gaf.algorithm.evaluation.node.Node;
import org.hda.gaf.algorithm.evaluation.node.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marcus on 19.04.16.
 */
public class HydrophobNeighborsCounterTest {

    @Before
    public void setup() {
    }

    @Test
    public void countZeroNeighbor() {
        Node[][] nodes = new Node[3][3];

        Node startNode = new Node(new Position(1, 1));

        nodes[1][1] = startNode;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(0, hydrophobNeighborsCount);
    }

    @Test
    public void nonHydrophobNeighbors() {
        directNeighbors('0', '0', 0);
    }

    @Test
    public void hydroAndNonHydroNeighbors() {
        directNeighbors('0', '1', 0);
    }

    @Test
    public void testCountOneNeighbor() {
        directNeighbors('1', '1', 1);
    }

    private void directNeighbors(char status_1, char status_2, int expectedNeighbors) {
        Node[][] nodes = new Node[5][5];

        Node startNode = new Node(new Position(2, 2));
        Node nextNode = new Node(startNode, new Position(2, 3));

        startNode.setHydrophob(status_1);
        nextNode.setHydrophob(status_2);

        nodes[2][2] = startNode;
        nodes[nextNode.getPosition().getX()][nextNode.getPosition().getY()] = nextNode;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(expectedNeighbors, hydrophobNeighborsCount);
    }

    @Test
    public void twoDirectNeighbors() {
        Node[][] nodes = new Node[4][4];

        Node startNode = new Node(new Position(1, 1));
        Node nextNode_1 = new Node(startNode, new Position(1, 2));
        Node nextNode_2 = new Node(nextNode_1, new Position(2, 2));

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('1');
        nextNode_2.setHydrophob('1');

        nodes[1][1] = startNode;
        nodes[2][1] = nextNode_1;
        nodes[2][2] = nextNode_2;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(2, hydrophobNeighborsCount);
    }

    @Test
    public void testIndirectNeighbors() {
        Node[][] nodes = new Node[4][4];

        Node startNode = new Node(new Position(1, 1));
        Node nextNode_1 = new Node(startNode, new Position(2, 1));
        Node nextNode_2 = new Node(nextNode_1, new Position(2, 2));
        Node nextNode_3 = new Node(nextNode_1, new Position(1, 2));

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('0');
        nextNode_2.setHydrophob('0');
        nextNode_3.setHydrophob('1');

        nodes[1][1] = startNode;
        nodes[2][1] = nextNode_1;
        nodes[2][2] = nextNode_2;
        nodes[1][2] = nextNode_3;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(1, hydrophobNeighborsCount);
    }

    @Test
    public void testOverlappingNeighbors() {
        Node[][] nodes = new Node[4][4];

        Position samePosition = new Position(2, 1);

        Node startNode = new Node(new Position(1, 1));
        Node nextNode_1 = new Node(startNode, samePosition);
        Node nextNode_2 = new Node(nextNode_1, samePosition);

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('1');
        nextNode_2.setHydrophob('1');

        nodes[1][1] = startNode;
        nodes[2][1] = nextNode_1;
        nodes[2][1] = nextNode_2;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(1, hydrophobNeighborsCount);
    }


}