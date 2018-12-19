package org.hda.gaf.algorithm.output;

import org.hda.gaf.algorithm.Population;
import org.hda.gaf.algorithm.PopulationStatistic;
import org.hda.gaf.algorithm.evaluation.node.Node;
import org.hda.gaf.algorithm.evaluation.node.Position;
import org.hda.gaf.algorithm.evaluation.node.Structure;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.time.Instant;

/**
 * Created by marcus on 17.05.16.
 */
@Getter
@Setter
public class GraphicOutput extends JFrame {

    private final int PROTEIN_WIDTH = 900;
    private final int PROTEIN_HEIGHT = 900;

    private final int WIDTH = 1600;
    private final int HEIGHT = 950;

    private final int TOTAL_MARGIN = 100;
    private final int MARGIN = 40;

    private Population population;
    private Structure protein;
    private BufferStrategy bs;

    public GraphicOutput() {
        super("Best protein");

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        while (!this.isDisplayable()) ;
        this.createBufferStrategy(2);
        while (bs == null) {
            bs = this.getBufferStrategy();
        }
    }

    @Override
    public void paint(Graphics g) {
        do {
//            do {
            Graphics2D g2 = null;
            try {
                g2 = (Graphics2D) bs.getDrawGraphics();
                drawStuff(g2);
            } finally {
                if (g2 != null) {
                    g2.dispose();
                }
            }
//            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());

    }

    private void drawStuff(Graphics2D g2) {
        g2.clearRect(0, 0, WIDTH, HEIGHT);

        Position lastPosition = null;

        this.protein = population.getBestProtein();

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
                scaling = (PROTEIN_WIDTH - TOTAL_MARGIN) / (maxWidth);
            } else {
                scaling = (PROTEIN_HEIGHT - TOTAL_MARGIN) / (maxHeight);
            }

            currentNode = protein.getStartNode();
            lastPosition = printNodeAndGetLastPosition(currentNode, g2, minX, minY, lastPosition, scaling);

            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();

                lastPosition = printNodeAndGetLastPosition(currentNode, g2, minX, minY, lastPosition, scaling);
            }
        }

        showStatistics(g2);
    }

    private void showStatistics(Graphics2D g2) {

        PopulationStatistic statistic = population.getStatistic();


        g2.setColor(Color.RED);
        g2.setFont(new Font("SansSerif", Font.BOLD, 17));
        g2.drawString("Generation " + statistic.getGeneration() + ":", 950, 50);
        g2.drawString("  Total Fitness: " + statistic.getTotalFitness(), 950, 75);
        g2.drawString("  Average Fitness " + statistic.getAverageFitness(), 950, 100);
        g2.drawString("  Average neighbor count: " + statistic.getAverageNeighborCounter(), 950, 125);
        g2.drawString("  Average overlap count: " + statistic.getAverageOverlapCounter(), 950, 150);
        g2.drawString("  Total Hamming distance: " + statistic.getTotalHammingDistance(), 950, 175);
        g2.drawString("  Average Hamming distance: " + statistic.getAverageHammingDistance(), 950, 200);
        g2.drawString("  Best overall: Fitness: " + statistic.getBestProtein().getAbsoluteFitness(), 950, 225);
        g2.drawString("  Best overall: Overlaps " + statistic.getBestProtein().getOverlappCounter(), 950, 250);
        g2.drawString("  Best overall: Valid neighbor count: " + statistic.getBestProtein().getValidNeighborCount(), 950, 275);
        g2.drawString("  Best in generation: absoluteFitness: " + statistic.getBestProteinInGeneration().getAbsoluteFitness(), 950, 300);
        g2.drawString("  Best in generation: Overlaps " + statistic.getBestProteinInGeneration().getOverlappCounter(), 950, 325);
        g2.drawString("  Best in generation: Valid neighbor count: " + statistic.getBestProteinInGeneration().getValidNeighborCount(), 950, 350);


        long timeElapsed = Instant.now().toEpochMilli() - population.getStartTime();
        g2.setColor(Color.BLACK);
        g2.drawString("Time taken: " + timeElapsed, 950, 500);
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
