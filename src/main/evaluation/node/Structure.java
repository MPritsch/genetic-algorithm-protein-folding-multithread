package main.evaluation.node;

import lombok.Data;
import lombok.NoArgsConstructor;
import main.evaluation.direction.RelativeDirection;

import java.util.List;

/**
 * Created by marcus on 03.05.16.
 */
@Data
@NoArgsConstructor
public class Structure {

    private boolean valid = true;
    private boolean overlapping = false;
    private int fitness;

    private Node startNode;
    private Node[][] nodes;

    private List<RelativeDirection> relativeDirections;

    public boolean isValidAndNotOverlappingStructure(){
        return valid && !overlapping;
    }
}
