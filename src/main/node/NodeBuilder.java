package main.node;

import main.direction.absolute.*;
import main.direction.relative.RelativeDirection;

/**
 * Created by marcus on 03.05.16.
 */
//TODO make it singleton
public class NodeBuilder {

    public Node createStartNode(RelativeDirection directionForNextNode) {
        return new Node(new Position(0, 0), directionForNextNode);
    }

    public Node createFollowingNode(Node previousNode, RelativeDirection directionForNextNode) {
        Node node = new Node(previousNode, directionForNextNode);
        return move(node);
    }

    private Node move(Node node) {
        Node previousNode = node.getPrevious();
        AbsoluteDirectionEnum previousDirection = previousNode.getMoveDoneInDirection();

        switch (previousDirection) {
            case NORTH:
                node = new North().setNextDirections(node);
                break;
            case EAST:
                node = new East().setNextDirections(node);
                break;
            case SOUTH:
                node = new South().setNextDirections(node);
                break;
            case WEST:
                node = new West().setNextDirections(node);
                break;
        }

        //TODO check if it works without
        previousNode.setNext(node);

        return node;
    }
}
