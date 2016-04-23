package main.node;

import main.node.Direction.Direction;

/**
 * Created by marcus on 19.04.16.
 */
public class Node {

    private int id;
    private Node previous;
    private Node next;

    private boolean hydrophob;

    private Position position;

    private Direction validDirection;

    //Startnode constructor
    public Node(int id, Position startPosition) {
        this.id = id;
        this.previous = null;
        this.position = startPosition;
    }

    //following nodes
    public Node(int id, Node previous, Direction validDirection) {
        this.id = id;
        this.previous = previous;
        this.validDirection = validDirection;

        setCurrentPosition(previous.getPosition(), validDirection);

        previous.setNext(this);
    }

    private void setCurrentPosition(Position previousPosition, Direction direction) {
        int xPosition = previousPosition.getX() + direction.xDirection;
        int yPosition = previousPosition.getY() + direction.yDirection;

        position = new Position(xPosition, yPosition, 0);
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

    public Direction getValidDirection() {
        return validDirection;
    }

    public void setValidDirection(Direction validDirection) {
        this.validDirection = validDirection;
    }
}