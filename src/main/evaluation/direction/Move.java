package main.evaluation.direction;

import main.Torus;

/**
 * Created by marcus on 07.05.16.
 */
public class Move {

    private int x;
    private int y;
    private int xLastMove;
    private int yLastMove;

    private final Torus torus;

    public Move(int x, int y, int structureLength) {
        this.x = x;
        this.y = y;
        xLastMove = 1; //first move in x direction
        yLastMove = 0;

        this.torus = new Torus(structureLength);
    }

    public void moveLeft() {
        int xOld = x;
        int yOld = y;
        x = x - yLastMove;
        y = y + xLastMove;
        calculateLastMove(xOld, yOld);

        convertPositionToTorus();
    }

    public void moveRight() {
        int xOld = x;
        int yOld = y;
        x = x + yLastMove;
        y = y - xLastMove;
        calculateLastMove(xOld, yOld);

        convertPositionToTorus();
    }

    private void calculateLastMove(int xOld, int yOld) {
        xLastMove = x - xOld;
        yLastMove = y - yOld;
    }

    public void moveStraight() {
        x = x + xLastMove;
        y = y + yLastMove;

        convertPositionToTorus();
    }

    private void convertPositionToTorus() {
        x = torus.convert(x);
        y = torus.convert(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
