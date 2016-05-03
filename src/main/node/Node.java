package main.node;

import lombok.Getter;
import lombok.Setter;
import main.direction.absolute.*;
import main.direction.relative.RelativeDirection;

/**
 * Created by marcus on 19.04.16.
 */
@Getter
@Setter
public class Node {

    private int id;
    private Node previous;
    private Node next;

    private boolean hydrophob;

    private Position position;

    private RelativeDirection directionForNextNode;
    private AbsoluteDirectionEnum moveDoneInDirection;

    //Startnode constructor
    public Node(Position startPosition, RelativeDirection directionForNextNode) {
        this.id = 0;
        this.previous = null;
        this.position = startPosition;
        this.directionForNextNode = directionForNextNode;
        this.moveDoneInDirection = AbsoluteDirectionEnum.EAST; //go any direction... right first
    }

    //following nodes
    public Node(Node previous, RelativeDirection directionForNextNode) {
        this.previous = previous;
        this.id = previous.getId();
        this.directionForNextNode = directionForNextNode;
        this.position = new Position(previous.getPosition().getX(), previous.getPosition().getY());

        this.previous.setNext(this);
    }

    public void setHydrophob(Character status) {
        this.hydrophob = (status.equals('1')) ? true : false;
    }
}