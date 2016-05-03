package main.direction.absolute;

import main.direction.relative.RelativeDirection;
import main.node.Node;
import main.node.Position;

/**
 * Created by marcus on 03.05.16.
 */
public interface AbsoluteDirection {

    Node setNextDirections(Node node);

    Node addDirectionToNode(Node node);
}
