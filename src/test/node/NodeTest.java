package test.node;

import main.node.Direction.Down;
import main.node.Direction.Left;
import main.node.Direction.Right;
import main.node.Direction.Up;
import main.node.Node;
import main.node.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marcus on 23.04.16.
 */
public class NodeTest {

    private Node startNode;

    @Before
    public void setup() {
        startNode = new Node(0, new Position(1, 1, 0));
    }

    @Test
    public void testUpDirection() {
        Node nextNode = new Node(1, startNode, new Up());

        Position nextPosition = nextNode.getPosition();

        assertEquals(1, nextPosition.getX());
        assertEquals(2, nextPosition.getY());
        assertEquals(0, nextPosition.getZ());
    }

    @Test
    public void testDownDirection() {
        Node nextNode = new Node(1, startNode, new Down());

        Position nextPosition = nextNode.getPosition();

        assertEquals(1, nextPosition.getX());
        assertEquals(0, nextPosition.getY());
        assertEquals(0, nextPosition.getZ());
    }

    @Test
    public void testRightDirection() {
        Node nextNode = new Node(1, startNode, new Right());

        Position nextPosition = nextNode.getPosition();

        assertEquals(2, nextPosition.getX());
        assertEquals(1, nextPosition.getY());
        assertEquals(0, nextPosition.getZ());
    }

    @Test
    public void testLeftDirection() {
        Node nextNode = new Node(1, startNode, new Left());

        Position nextPosition = nextNode.getPosition();

        assertEquals(0, nextPosition.getX());
        assertEquals(1, nextPosition.getY());
        assertEquals(0, nextPosition.getZ());
    }
}
