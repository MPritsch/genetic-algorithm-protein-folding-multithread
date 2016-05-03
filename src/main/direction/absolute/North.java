package main.direction.absolute;

import lombok.NoArgsConstructor;
import main.direction.relative.RelativeDirection;
import main.node.Node;
import main.node.Position;

/**
 * Created by marcus on 03.05.16.
 */
@NoArgsConstructor
public class North implements AbsoluteDirection {

    private int x = 1;
    private int y = 0;

    public Node setNextDirections(Node node) {
        RelativeDirection directionToGo = node.getPrevious().getDirectionForNextNode();

        switch (directionToGo) {
            case LEFT:
                node.setMoveDoneInDirection(AbsoluteDirectionEnum.WEST);
                node = new West().addDirectionToNode(node);
                break;
            case RIGHT:
                node.setMoveDoneInDirection(AbsoluteDirectionEnum.EAST);
                node = new East().addDirectionToNode(node);
                break;
            case STRAIGHT:
                node.setMoveDoneInDirection(AbsoluteDirectionEnum.NORTH);
                node = new North().addDirectionToNode(node);
                break;
        }

        return node;
    }

    public Node addDirectionToNode(Node node) {
        int previousX = node.getPrevious().getPosition().getX();
        int previousY = node.getPrevious().getPosition().getY();
        Position position = new Position(previousX + x, previousY + y);

        node.setPosition(position);
        return node;
    }
}
