package test;

import main.counter.CrossoverCounter;
import main.node.Node;
import main.node.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by marcus on 19.04.16.
 */
public class CrossoverCounterTest {

    @Test
    public void testNoCrossoverCount() {
        List<Node>[][] nodes = new List[1][1];

        List<Node> nodeList = new ArrayList<>();

        Position position = new Position(0, 0);

        Node node_1 = new Node(0, null, position);

        nodeList.add(node_1);

        nodes[0][0] = nodeList;

        int crossoverCount = new CrossoverCounter().countCrossovers(nodes);

        assertEquals(crossoverCount, 0);
    }

    @Test
    public void testOneCrossoverCount() {
        List<Node>[][] nodes = new List[1][1];

        List<Node> nodeList = new ArrayList<>();

        Position samePosition = new Position(0, 0);

        Node node_1 = new Node(0, null, samePosition);
        Node node_2 = new Node(1, node_1, samePosition);
        node_1.setNext(node_2);

        nodeList.add(node_1);
        nodeList.add(node_2);

        nodes[0][0] = nodeList;

        int crossoverCount = new CrossoverCounter().countCrossovers(nodes);

        assertEquals(crossoverCount, 1);
    }

    @Test
    public void testTwoCrossoverCount() {
        List<Node>[][] nodes = new List[1][1];

        List<Node> nodeList = new ArrayList<>();

        Position samePosition = new Position(0, 0);

        Node node_1 = new Node(0, null, samePosition);
        Node node_2 = new Node(1, node_1, samePosition);
        Node node_3 = new Node(2, node_2, samePosition);
        node_1.setNext(node_2);
        node_2.setNext(node_3);

        nodeList.add(node_1);
        nodeList.add(node_2);
        nodeList.add(node_3);

        nodes[0][0] = nodeList;

        int crossoverCount = new CrossoverCounter().countCrossovers(nodes);

        assertEquals(crossoverCount, 2);
    }

}