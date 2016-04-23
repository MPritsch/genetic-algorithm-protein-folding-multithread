package test.counter;

import main.counter.HydrophobNeighborsCounter;
import main.node.Direction.*;
import main.node.Node;
import main.node.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by marcus on 19.04.16.
 */
public class HydrophobNeighborsCounterTest {

    private List<Node> startNodeList;
    private List<Node> nextNodeList_1;
    private List<Node> nextNodeList_2;
    private List<Node> nextNodeList_3;

    @Before
    public void setup() {
        startNodeList = new ArrayList<>();
        nextNodeList_1 = new ArrayList<>();
        nextNodeList_2 = new ArrayList<>();
        nextNodeList_3 = new ArrayList<>();
    }

    @Test
    public void testCountZeroNeighbor() {
        List<Node>[][] nodes = new ArrayList[3][3];

        Node startNode = new Node(0, new Position(1, 1, 0));

        startNodeList.add(startNode);

        nodes[1][1] = startNodeList;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(0, hydrophobNeighborsCount);
    }

    @Test
    public void testNonHydrophobNeighbors() {
        testDirectNeighborsInAllDirections('0', '0', 0);
    }

    @Test
    public void testHydroAndNonHydroNeighbors() {
        testDirectNeighborsInAllDirections('0', '1', 0);
    }

    @Test
    public void testCountOneNeighbor() {
        testDirectNeighborsInAllDirections('1', '1', 1);
    }

    private void testDirectNeighborsInAllDirections(char status_1, char status_2, int expectedNeighbors) {
        testDirectNeighbors(status_1, status_2, new Right(), expectedNeighbors);
        testDirectNeighbors(status_1, status_2, new Left(), expectedNeighbors);
        testDirectNeighbors(status_1, status_2, new Down(), expectedNeighbors);
        testDirectNeighbors(status_1, status_2, new Up(), expectedNeighbors);
    }

    private void testDirectNeighbors(char status_1, char status_2, Direction direction, int expectedNeighbors) {
        List<Node>[][] nodes = new ArrayList[4][4];

        Node startNode = new Node(0, new Position(1, 1, 0));
        Node nextNode = new Node(1, startNode, direction);

        startNode.setHydrophob(status_1);
        nextNode.setHydrophob(status_2);

        startNodeList.add(startNode);
        nextNodeList_1.add(nextNode);

        nodes[1][1] = startNodeList;
        nodes[2][1] = nextNodeList_1;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(expectedNeighbors, hydrophobNeighborsCount);

        startNodeList.clear();
        nextNodeList_1.clear();
    }

    @Test
    public void testCountTwoDirectNeighbors() {
        List<Node>[][] nodes = new ArrayList[4][4];

        Node startNode = new Node(0, new Position(1, 1, 0));
        Node nextNode_1 = new Node(1, startNode, new Right());
        Node nextNode_2 = new Node(2, nextNode_1, new Up());

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('1');
        nextNode_2.setHydrophob('1');

        startNodeList.add(startNode);
        nextNodeList_1.add(nextNode_1);
        nextNodeList_2.add(nextNode_2);

        nodes[1][1] = startNodeList;
        nodes[2][1] = nextNodeList_1;
        nodes[2][2] = nextNodeList_2;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(2, hydrophobNeighborsCount);
    }

    @Test
    public void testIndirectNeighbors() {
        List<Node>[][] nodes = new ArrayList[4][4];

        Node startNode = new Node(0, new Position(1, 1, 0));
        Node nextNode_1 = new Node(1, startNode, new Right());
        Node nextNode_2 = new Node(2, nextNode_1, new Up());
        Node nextNode_3 = new Node(2, nextNode_1, new Left());

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('0');
        nextNode_2.setHydrophob('0');
        nextNode_3.setHydrophob('1');

        startNodeList.add(startNode);
        nextNodeList_1.add(nextNode_1);
        nextNodeList_2.add(nextNode_2);
        nextNodeList_3.add(nextNode_3);

        nodes[1][1] = startNodeList;
        nodes[2][1] = nextNodeList_1;
        nodes[2][2] = nextNodeList_2;
        nodes[1][2] = nextNodeList_3;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(1, hydrophobNeighborsCount);
    }

    @Test
    public void testOverlappingNeighbors() {
        List<Node>[][] nodes = new ArrayList[4][4];

        Node startNode = new Node(0, new Position(1, 1, 0));
        Node nextNode_1 = new Node(1, startNode, new Right());
        Node nextNode_2 = new Node(2, nextNode_1, new Up());

        startNode.setHydrophob('1');
        nextNode_1.setHydrophob('1');
        nextNode_2.setHydrophob('1');

        nextNode_2.setPosition(nextNode_1.getPosition());

        startNodeList.add(startNode);
        nextNodeList_1.add(nextNode_1);
        nextNodeList_1.add(nextNode_2);

        nodes[1][1] = startNodeList;
        nodes[2][1] = nextNodeList_1;

        int hydrophobNeighborsCount = new HydrophobNeighborsCounter(nodes).countHydrophobNeighbors(startNode);

        assertEquals(2, hydrophobNeighborsCount);
    }


}