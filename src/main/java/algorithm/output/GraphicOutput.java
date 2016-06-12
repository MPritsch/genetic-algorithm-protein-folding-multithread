package algorithm.output;

import algorithm.evaluation.node.Node;
import algorithm.evaluation.node.Position;
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

    private final int TOTAL_MARGIN = 100;
    private final int MARGIN = 40;

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
        //get minimum and maximum of width and height -> substract minimum
        //startidx - objectpos
        while (bs == null) {
            //todo fix concurrency problem...
        }
        Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();

        g2.clearRect(0, 0, WIDTH, HEIGHT);

        Position lastPosition = null;

        if (protein.getNodes() != null) {

            //get max/min width/height
            Node currentNode = protein.getStartNode();
            Integer minX = currentNode.getAbsolutePosition().getX();
            Integer minY = currentNode.getAbsolutePosition().getY();
            Integer maxX = currentNode.getAbsolutePosition().getX();
            Integer maxY = currentNode.getAbsolutePosition().getY();

            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();

                int x = currentNode.getAbsolutePosition().getX();
                int y = currentNode.getAbsolutePosition().getY();

                minX = Math.min(minX, x);
                minY = Math.min(minY, y);

                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
            }

            Integer maxWidth = maxX - minX;
            Integer maxHeight = maxY - minY;

            Integer scaling;

            if (maxWidth > maxHeight) {
                scaling = (WIDTH - TOTAL_MARGIN) / (maxWidth);
            } else {
                scaling = (HEIGHT - TOTAL_MARGIN) / (maxHeight);
            }

            currentNode = protein.getStartNode();
            lastPosition = printNodeAndGetLastPosition(currentNode, g2, minX, minY, lastPosition, scaling);

            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();

                lastPosition = printNodeAndGetLastPosition(currentNode, g2, minX, minY, lastPosition, scaling);
            }
        }
        bs.show();
    }

    private Position printNodeAndGetLastPosition(Node currentNode, Graphics2D g2, Integer minX, Integer minY, Position lastPosition, Integer scaling) {
        Color color;
        if (currentNode == protein.getStartNode() || currentNode.getNext() == null) { //start or end
            color = currentNode.isHydrophob() ? new Color(71, 83, 140) : new Color(200, 154, 154);
        } else {
            color = currentNode.isHydrophob() ? Color.BLACK : Color.WHITE;
        }
        g2.setColor(color);

        Integer xPos = (currentNode.getAbsolutePosition().getX() - minX) * scaling + MARGIN;
        Integer yPos = (currentNode.getAbsolutePosition().getY() - minY) * scaling + MARGIN;

        g2.fillOval(xPos, yPos, scaling - 1, scaling - 1);
        g2.setColor(Color.BLACK);
        g2.drawOval(xPos, yPos, scaling - 1, scaling - 1);

        if (lastPosition != null) {
            Integer xLastPos = lastPosition.getX();
            Integer yLastPos = lastPosition.getY();

            g2.setColor(Color.RED);
            g2.drawLine(xPos + (scaling / 2), yPos + (scaling / 2), xLastPos + (scaling / 2), yLastPos + (scaling / 2));
        } else {
            lastPosition = new Position(xPos, yPos);
        }

        lastPosition.setX(xPos);
        lastPosition.setY(yPos);

        return lastPosition;
    }
}
