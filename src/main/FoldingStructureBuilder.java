package main;

import main.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 19.04.16.
 */
public class FoldingStructureBuilder {

    public static List<Node>[][] buildStructureAndStartNode(String primarySequence, Node startNode){
        List<Node>[][] nodes = new ArrayList[0][];

        return nodes;
    }

//    private void fillNodesWithStatus(Node startNode) {
//        Node node = startNode;
//
//        for (int i = 0; i < primarySq.length(); i++) {
//            Character status = primarySq.charAt(i);
//            node.setHydrophob(status);
//
//            node = node.getNext();
//        }
//    }
//
//    public int calculatePrimarySequenzEnergy() {
//        int energy = 0;
//
//        Character previousAmino = null;
//
//        for (int i = 0; i < primarySq.length(); i++) {
//            Character amino = primarySq.charAt(i);
//
//            if (previousAmino != null) {
//                if (previousAmino.equals('1') && amino.equals('1')) {
//                    energy++;
//                }
//            }
//            previousAmino = amino;
//        }
//
//        return energy;
//    }
}
