package algorithm;

import algorithm.evaluation.node.Node;
import algorithm.evaluation.node.Structure;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by marcus on 17.05.16.
 */
@Getter
@Setter
public class GraphicOutput extends JFrame {

    private final int WIDTH = 900;
    private final int HEIGHT = 900;

    private Structure protein;
    private BufferStrategy bs;

    public GraphicOutput() {
        super("Best protein");

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.createBufferStrategy(2);
        bs = this.getBufferStrategy();
    }

    @Override
    public void paint(Graphics g) {
        //todo move further left (relocate??)
        Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();

        g2.clearRect(0, 0, WIDTH, HEIGHT);

        int scaling = 5;

        Integer xPos;
        Integer yPos;
        Integer xLastPos = null;
        Integer yLastPos = null;

        if (protein.getNodes() != null) {
            Node currentNode = protein.getStartNode();

            while(currentNode.getNext() != null){
                Color color = currentNode.isHydrophob() ? Color.BLACK : Color.WHITE;
                g2.setColor(color);

                xPos = currentNode.getPosition().getX() * scaling;
                yPos = currentNode.getPosition().getY() * scaling;

                g2.fillOval(xPos, yPos, scaling - 1, scaling - 1);
                g2.setColor(Color.BLACK);
                g2.drawOval(xPos, yPos, scaling - 1, scaling - 1);

                if (xLastPos != null && yLastPos != null) {
                    g2.setColor(Color.RED);
                    g2.drawLine(xPos + (scaling / 2), yPos + (scaling / 2), xLastPos + (scaling / 2), yLastPos + (scaling / 2));
                }
                xLastPos = xPos;
                yLastPos = yPos;

                currentNode = currentNode.getNext();
            }
        }
        bs.show();
    }

    private Node[][] rotate(Node[][] nodes) {
        Node[][] newNodes = new Node[nodes.length][nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                newNodes[i][j] = nodes[j][i];
            }
        }
        return newNodes;
    }
}
