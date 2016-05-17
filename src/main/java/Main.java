import algorithm.GeneticAlgorithm;
import algorithm.GraphicOutput;
import algorithm.Population;
import algorithm.evaluation.node.Structure;
import algorithm.examples.Examples;
import com.google.common.base.Stopwatch;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {

    final static int GENERATION_AMOUNT = 163;
    final static int POPULATION_AMOUNT = 300;
    final static float MUTATION_RATE = 0.01F;
    final static float CROSSOVER_RATE = 0.25F;

    final static String PRIMARY_SEQUENCE = Examples.SEQ48;

    public static void main(String[] args) {
        Stopwatch s = Stopwatch.createStarted();
        Population population = new GeneticAlgorithm()
                .usesPrimarySequence(PRIMARY_SEQUENCE)
                .hasGenerations(GENERATION_AMOUNT)
                .hasPopulationAmountOf(POPULATION_AMOUNT)
                .hasCrossoverRateOf(CROSSOVER_RATE)
                .hasMutationRateOf(MUTATION_RATE)
                .generate();


        System.out.println("Calculation took: " + s.elapsed(TimeUnit.MILLISECONDS));
        saveChart(population);

//        GraphicOutput frame = new GraphicOutput();
//        frame.setProtein(population.getBestProtein());
//        frame.revalidate();
    }

    private static void saveChart(Population population) {
        DefaultCategoryDataset lineChartDataset = population.getLineChartDataset();

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Genetic Algorithm flow", "Generation",
                "Fitness",
                lineChartDataset, PlotOrientation.VERTICAL,
                true, true, false);

        File lineChart = new File("GeneticAlgorithm.jpeg");

        try {
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, 1280, 720);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
