package org.hda.gaf;

import org.hda.gaf.algorithm.evaluation.direction.Move;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by marcus on 07.05.16.
 */
public class MoveTest {

    private Move move;

    @Test
    public void moveLeft() {
        move = new Move(2, 3, 5);

        move.moveLeft();
        assertMove(2, 4);

        move.moveLeft();
        assertMove(1, 4);
    }

    @Test
    public void moveRight() {
        move = new Move(2, 3, 5);

        move.moveRight();
        assertMove(2, 2);

        move.moveRight();
        assertMove(1, 2);
    }

    @Test
    public void moveStraight() {
        move = new Move(2, 3, 5);

        move.moveStraight();
        assertMove(3, 3);

        move.moveStraight();
        assertMove(4, 3);
    }

    @Test
    public void moveOver() {
        move = new Move(4, 4, 5);

        move.moveStraight();
        assertMove(0, 4);
    }

    @Test
    public void moveUnder() {
        move = new Move(2, 0, 5);

        move.moveRight();
        assertMove(2, 4);
    }

    private void assertMove(int expectedX, int expectedY) {
        assertThat(move.getX()).isEqualTo(expectedX);
        assertThat(move.getY()).isEqualTo(expectedY);
    }
}