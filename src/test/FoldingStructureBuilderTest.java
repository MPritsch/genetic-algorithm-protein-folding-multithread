//package test;
//
//import main.FoldingStructureBuilder;
//import main.node.Node;
//import main.node.Position;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by marcus on 23.04.16.
// */
//public class FoldingStructureBuilderTest {
//
//    @Test
//    public void testBuildNoNode() {
//        List<Node>[][] structure = FoldingStructureBuilder.buildStructure("", null);
//
//        assertEquals(0, structure.length);
//    }
//
//    @Test
//    public void testBuildSingleNode() {
//        Node startNode = new Node(0, new Position(0, 0, 0));
//        List<Node>[][] structure = FoldingStructureBuilder.buildStructure("1", startNode);
//
//        assertEquals(3, structure.length);
//        assertEquals(3, structure[0].length);
//
//        Node node = structure[0][0].get(0);
//        assertNotNull(node);
//        assertEquals(0, node.getId());
//        assertTrue(node.getPosition().equals(new Position(0, 0, 0)));
//        assertTrue(node.isHydrophob());
//
//        assertNull(structure[0][1]);
//    }
//
//    @Test
//    public void testBuildTwoNodes() {
//        Node startNode = new Node(0, new Position(0, 0, 0));
//        Node nextNode = new Node(1, startNode, new Right());
//        List<Node>[][] structure = FoldingStructureBuilder.buildStructure("10", startNode);
//
//        assertEquals(5, structure.length);
//        assertEquals(5, structure[0].length);
//
//        assertEquals(1, structure[0][0].size());
//
//        Node node = structure[0][0].get(0);
//        assertNotNull(node);
//        assertTrue(node.isHydrophob());
//
//        assertEquals(1, structure[1][0].size());
//
//        node = structure[1][0].get(0);
//        assertNotNull(node);
//        assertFalse(node.isHydrophob());
//    }
//
//    @Test
//    public void buildOverlappingNodes() {
//        Node startNode = new Node(0, new Position(0, 0, 0));
//        Node nextNode = new Node(1, startNode, new Right());
//        nextNode.setPosition(new Position(0, 0, 0));
//        List<Node>[][] structure = FoldingStructureBuilder.buildStructure("01", startNode);
//
//        assertEquals(5, structure.length);
//        assertEquals(5, structure[0].length);
//
//        assertEquals(2, structure[0][0].size());
//
//        Node node = structure[0][0].get(0);
//        assertNotNull(node);
//        assertFalse(node.isHydrophob());
//        assertEquals(0, node.getPosition().getZ());
//
//        node = structure[0][0].get(1);
//        assertNotNull(node);
//        assertTrue(node.isHydrophob());
//        assertEquals(1, node.getPosition().getZ());
//    }
//
//    //TODO make broken sequences impossible?
//    @Test
//    public void buildBrokenStructure() {
//        Node startNode = new Node(0, new Position(0, 0, 0));
//        Node nextNode = new Node(1, startNode, new Right());
//        nextNode.setPosition(new Position(3, 3, 0));
//
//        List<Node>[][] structure = FoldingStructureBuilder.buildStructure("11", startNode);
//
//        assertEquals(5, structure.length);
//        assertEquals(5, structure[0].length);
//
//        assertEquals(1, structure[0][0].size());
//
//        Node node = structure[0][0].get(0);
//        assertNotNull(node);
//        assertTrue(node.isHydrophob());
//
//        assertEquals(1, structure[3][3].size());
//
//        node = structure[3][3].get(0);
//        assertNotNull(node);
//        assertTrue(node.isHydrophob());
//    }
//}
