package org.hda.gaf;

import com.google.common.base.Stopwatch;
import org.hda.gaf.algorithm.Population;
import org.hda.gaf.threads.ThreadAlgorithmExecutor;
import org.hda.gaf.threads.ThreadStatisticsSharer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.hda.gaf.DefaultOptions.POPULATION_AMOUNT;
import static org.hda.gaf.DefaultOptions.THREAD_AMOUNT;

public class Main {

    public static void main(String[] args) {
        Stopwatch s = Stopwatch.createStarted();

        Population bestPopulation = new ThreadAlgorithmExecutor(args).executeAndGetBestPopulation();

        int globalGenerationCount = ThreadStatisticsSharer.getGlobalGenerationCount();

//        System.out.println("Current generation without size: " + geneticAlgorithm.getCurrentGeneration());
        System.out.println("Size: " + THREAD_AMOUNT);
//            int calculatedGenerations = geneticAlgorithm.getCurrentGeneration() * size;
        int calculatedIndividuals = POPULATION_AMOUNT * globalGenerationCount;
        System.out.println("Calculation took: " + s.elapsed(TimeUnit.MILLISECONDS) + " ms");
        System.out.println("Calculation generations: " + globalGenerationCount);
        System.out.println("Calculation individuals: " + calculatedIndividuals);


        bestPopulation.printStatusOfCurrentGeneration(globalGenerationCount, bestPopulation.getBestProtein());
        saveChart(bestPopulation);
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
