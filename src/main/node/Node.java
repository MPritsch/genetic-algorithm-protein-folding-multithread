package main.node;

/**
 * Created by marcus on 19.04.16.
 */
public class Node {

    private int id;
    private Node previous;
    private Node next;

    private boolean hydrophob;

    private Position position;

    public Node() {

    }

    public Node(int id, Node previous, Position position) {
        this.id = id;
        this.previous = previous;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public boolean isHydrophob() {
        return hydrophob;
    }

    public void setHydrophob(Character status) {
        this.hydrophob = (status.equals('1')) ? true : false;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
