import algorithm.evaluation.FoldingStructureBuilder;
import algorithm.evaluation.direction.RelativeDirection;
import algorithm.evaluation.node.Node;
import algorithm.evaluation.node.Position;
import algorithm.evaluation.node.Structure;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static algorithm.evaluation.direction.RelativeDirection.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * Created by marcus on 23.04.16.
 */
public class FoldingStructureBuilderTest {

    FoldingStructureBuilder foldingStructureBuilder;

    @Test
    public void emptyPrimarySequence() {
        foldingStructureBuilder = new FoldingStructureBuilder("");

        Structure structure = foldingStructureBuilder.buildStructure(Arrays.asList(RIGHT, LEFT));

        assertThat(structure.isValid()).isFalse();
    }

    @Test
    public void emptyDirections() {
        foldingStructureBuilder = new FoldingStructureBuilder("asdfasdf");

        Structure structure = foldingStructureBuilder.buildStructure(new ArrayList<>());

        assertThat(structure.isValid()).isFalse();
    }

    @Test
    public void invalidInputSize() {
        //primarySequnce size = directions size - 1
        foldingStructureBuilder = new FoldingStructureBuilder("1001");

        Structure structure = foldingStructureBuilder.buildStructure(Arrays.asList(LEFT));

        assertThat(structure.isValid()).isFalse();
    }

    @Test
    public void createOverlappingFolding() {
        foldingStructureBuilder = new FoldingStructureBuilder("11111");
        Structure structure = foldingStructureBuilder.buildStructure(Arrays.asList(RIGHT, RIGHT, RIGHT, RIGHT));

        assertThat(structure.isValid()).isTrue();
        assertThat(structure.getOverlappCounter()).isEqualTo(1);
    }

    @Test
    public void createValidFolding() {
        String primarySequence = "11110";
        foldingStructureBuilder = new FoldingStructureBuilder(primarySequence);
        Structure structure = foldingStructureBuilder.buildStructure(Arrays.asList(LEFT, RIGHT, RIGHT, STRAIGHT));
        Node[][] nodes = structure.getNodes();

        assertThat(structure.isValid()).isTrue();
        assertThat(structure.getOverlappCounter()).isEqualTo(0);

        Node node = structure.getStartNode();
        assertNode(node, nodes, 0, new Position(2, 3), primarySequence.charAt(0));

        //left
        node = node.getNext();
        assertNode(node, nodes, 1, new Position(2, 4), primarySequence.charAt(1));

        //right
        node = node.getNext();
        assertNode(node, nodes, 2, new Position(3, 4), primarySequence.charAt(2));

        //right
        node = node.getNext();
        assertNode(node, nodes, 3, new Position(3, 3), primarySequence.charAt(3));

        //straight
        node = node.getNext();
        assertNode(node, nodes, 4, new Position(3, 2), primarySequence.charAt(4));

        assertThat(node.getNext()).isNull();
    }

    private void assertNode(Node node, Node[][] nodes, int id, Position position, char status) {
        assertThat(node.getId()).isEqualTo(id);
        assertThat(node.getPosition()).isEqualToComparingFieldByField(position);
        assertThat(node).isEqualTo(nodes[position.getX()][position.getY()]);
        if (status == '1') {
            assertThat(node.isHydrophob()).isTrue();
        } else if (status == '0') {
            assertThat(node.isHydrophob()).isFalse();
        } else {
            assertThat(false).isTrue();
        }
    }


}
