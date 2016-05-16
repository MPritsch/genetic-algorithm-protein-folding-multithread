import algorithm.GeneticAlgorithm;
import algorithm.Population;
import algorithm.examples.Examples;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;

public class Main {

    final static int GENERATION_AMOUNT = 10;
    final static int POPULATION_AMOUNT = 200;
    final static float MUTATION_RATE = 0.01F;
    final static float CROSSOVER_RATE = 0.25F;

    final static String PRIMARY_SEQUENCE = Examples.SEQ20;

    public static void main(String[] args) {
        Population population = new GeneticAlgorithm()
                .usesPrimarySequence(PRIMARY_SEQUENCE)
                .hasGenerations(GENERATION_AMOUNT)
                .hasPopulationAmountOf(POPULATION_AMOUNT)
                .hasCrossoverRateOf(CROSSOVER_RATE)
                .hasMutationRateOf(MUTATION_RATE)
                .generate();

        saveChart(population);
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
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
