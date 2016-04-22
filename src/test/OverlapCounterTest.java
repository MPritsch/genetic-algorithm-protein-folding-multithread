package test;

import main.counter.OverlapCounter;
import main.node.Direction.Up;
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
public class OverlapCounterTest {

    private List<Node> nodeList;

    @Before
    public void setup(){
        nodeList = new ArrayList<>();
    }

    @Test
    public void testNoCrossoverCount() {
        List<Node>[][] nodes = new ArrayList[1][1];

        Position position = new Position(0, 0, 0);

        Node startNode = new Node(0, position);

        nodeList.add(startNode);
        nodes[0][0] = nodeList;

        int overlapCount = new OverlapCounter().countOverlaps(nodes);

        assertEquals(0, overlapCount);
    }

    @Test
    public void testOneCrossoverCount() {
        List<Node>[][] nodes = new ArrayList[1][1];

        Position startPosition = new Position(0, 0, 0);

        Node startNode = new Node(0, startPosition);
        Node nextNode = new Node(1, startNode, new Up());

        nodeList.add(startNode);
        nodeList.add(nextNode);
        nodes[0][0] = nodeList;

        int overlapCount = new OverlapCounter().countOverlaps(nodes);

        assertEquals(1, overlapCount);
    }

    @Test
    public void testTwoCrossoverCount() {
        List<Node>[][] nodes = new ArrayList[1][1];

        Position startPosition = new Position(0, 0, 0);

        Node startNode = new Node(0, startPosition);
        Node nextNode_1 = new Node(1, startNode, new Up());
        Node nextNode_2 = new Node(2, nextNode_1, new Up());

        nodeList.add(startNode);
        nodeList.add(nextNode_1);
        nodeList.add(nextNode_2);
        nodes[0][0] = nodeList;

        int overlapCount = new OverlapCounter().countOverlaps(nodes);

        assertEquals(2, overlapCount);
    }

}