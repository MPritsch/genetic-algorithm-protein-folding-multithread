package algorithm.evaluation.node;

import lombok.Getter;
import lombok.Setter;

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

    //Startnode constructor
    public Node(Position startPosition) {
        this.id = 0;
        this.previous = null;
        this.position = startPosition;
    }

    //following nodes
    public Node(Node previous, Position position) {
        this.previous = previous;
        this.id = previous.getId() + 1;
        this.position = position;

        this.previous.setNext(this);
    }

    public void setHydrophob(Character status) {
        this.hydrophob = (status.equals('1')) ? true : false;
    }
}