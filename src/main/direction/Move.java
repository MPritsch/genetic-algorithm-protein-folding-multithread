package main.direction;

/**
 * Created by marcus on 07.05.16.
 */
public class Move {

    private int x;
    private int y;
    private int xLastMove;
    private int yLastMove;

    private final int structureLength;

    public Move(int x, int y, int structureLength) {
        this.x = x;
        this.y = y;
        xLastMove = 1; //first move in x direction
        yLastMove = 0;

        this.structureLength = structureLength;
    }

    public void moveLeft() {
        int xOld = x;
        int yOld = y;
        x = x - yLastMove;
        y = y + xLastMove;
        xLastMove = x - xOld;
        yLastMove = y - yOld;

        convertPositionToTorus();
    }

    public void moveRight() {
        int xOld = x;
        int yOld = y;
        x = x + yLastMove;
        y = y - xLastMove;
        xLastMove = x - xOld;
        yLastMove = y - yOld;

        convertPositionToTorus();
    }

    public void moveStraight() {
        x = x + xLastMove;
        y = y + yLastMove;

        convertPositionToTorus();
    }

    private void convertPositionToTorus() {
        x = convertToTorusCoordinate(x);
        y = convertToTorusCoordinate(y);
    }

    private int convertToTorusCoordinate(int coordinate) {
        return (coordinate + structureLength) % structureLength;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
