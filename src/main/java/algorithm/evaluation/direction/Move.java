package algorithm.evaluation.direction;


import algorithm.Torus;

/**
 * Created by marcus on 07.05.16.
 */
public class Move {

    private int x;
    private int y;
    private int xAbsolute;
    private int yAbsolute;
    private int xLastMove;
    private int yLastMove;

    private final Torus torus;

    public Move(int x, int y, int structureLength) {
        this.x = x;
        this.y = y;
        this.xAbsolute = x;
        this.yAbsolute = y;
        xLastMove = 1; //first move in x direction
        yLastMove = 0;

        this.torus = new Torus(structureLength);
    }

    public void moveLeft() {
        int xOld = xAbsolute;
        int yOld = yAbsolute;
        xAbsolute = xAbsolute - yLastMove;
        yAbsolute = yAbsolute + xLastMove;
        calculateLastMove(xOld, yOld);

        convertPositionToTorus();
    }

    public void moveRight() {
        int xOld = xAbsolute;
        int yOld = yAbsolute;
        xAbsolute = xAbsolute + yLastMove;
        yAbsolute = yAbsolute - xLastMove;
        calculateLastMove(xOld, yOld);

        convertPositionToTorus();
    }

    private void calculateLastMove(int xOld, int yOld) {
        xLastMove = xAbsolute - xOld;
        yLastMove = yAbsolute - yOld;
    }

    public void moveStraight() {
        xAbsolute = xAbsolute + xLastMove;
        yAbsolute = yAbsolute + yLastMove;

        convertPositionToTorus();
    }

    private void convertPositionToTorus() {
        x = torus.convert(xAbsolute);
        y = torus.convert(yAbsolute);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXAbsolute() {
        return xAbsolute;
    }

    public int getYAbsolute() {
        return yAbsolute;
    }
}
