package main;

/**
 * Created by marcus on 08.05.16.
 */
public class Torus {

    private int structureLength;

    public Torus(int structureLength) {
        this.structureLength = structureLength;
    }

    public int convert(int coordinate) {
        return (coordinate + structureLength) % structureLength;
    }
}
