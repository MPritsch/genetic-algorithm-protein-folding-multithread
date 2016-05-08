//package test.counter;
//
//import DirectHydrophobNeighborsCounter;
//import Node;
//import Position;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by marcus on 19.04.16.
// */
//public class DirectNeighborCounterTest {
//
//    @Test
//    public void testEmptyNodes() {
//        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(null);
//
//        assertEquals(0, count);
//    }
//
//    @Test
//    public void testCountZeroDirectNeighbors() {
//        Node startNode = new Node(0, new Position(0,0,0));
//
//        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode);
//
//        assertEquals(0, count);
//    }
//
//    @Test
//    public void testCountOneDirectNeighbor() {
//        testCountOnTwoNeighbors('1', '1', 1);
//    }
//
//    @Test
//    public void testNonHydroNeighbors() {
//        testCountOnTwoNeighbors('0', '0', 0);
//    }
//
//    @Test
//    public void testHydroAndNonHydroNeighbors() {
//        testCountOnTwoNeighbors('0', '1', 0);
//    }
//
//    private void testCountOnTwoNeighbors(char status_1, char status_2, int expectedNeighbors){
//        Node startNode = new Node(0, new Position(0,0,0));
//        Node nextNode = new Node(1, startNode, new Right());
//
//        startNode.setHydrophob(status_1);
//        nextNode.setHydrophob(status_2);
//
//        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode);
//
//        assertEquals(expectedNeighbors, count);
//    }
//
//    @Test
//    public void testCountTwoNeighbors() {
//        testCountOnThreeNeighbors('1', '1', '1', 2);
//    }
//
//    @Test
//    public void testNonHydrophobInTheMiddle() {
//        testCountOnThreeNeighbors('1', '0', '1', 0);
//    }
//
//    private void testCountOnThreeNeighbors(char status_1, char status_2, char status_3, int expectedNeighbors){
//        Node startNode = new Node(0, new Position(0,0,0));
//        Node nextNode_1 = new Node(1, startNode, new Right());
//        Node nextNode_2 = new Node(2, nextNode_1, new Right());
//
//
//        startNode.setHydrophob(status_1);
//        nextNode_1.setHydrophob(status_2);
//        nextNode_2.setHydrophob(status_3);
//
//        int count = new DirectHydrophobNeighborsCounter().countDirectNeighbors(startNode);
//
//        assertEquals(expectedNeighbors, count);
//    }
//
//}