import algorithm.geneticalgorithm.TimeLimitedAlgorithm;
import algorithm.geneticalgorithm.GeneticAlgorithm;
import algorithm.Population;
import algorithm.examples.Examples;
import algorithm.selectionalgorithm.FitnessProportionalSelectionAlgorithm;
import algorithm.selectionalgorithm.TunierBestFitnessSelectionAlgorithm;
import algorithm.selectionalgorithm.TunierFitnessProportionalSelectionAlgorithm;
import com.google.common.base.Stopwatch;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Main {

    final static boolean DOCUMENTS_STATISTIC = false;

    final static int GENERATION_AMOUNT = 163;
    final static long TIME_LIMIT = 20000;
    final static int POPULATION_AMOUNT = 500;
    final static float MUTATION_RATE = 0.02F;
    final static float CROSSOVER_RATE = 0.25F;

    final static int CANDIDATE_AMOUNT_PER_SELECTION = 200;

    final static boolean CALC_HEMMING_DISTANCE = false;

    final static String PRIMARY_SEQUENCE = Examples.SEQ300;

    public static void main(String[] args) {
        Stopwatch s = Stopwatch.createStarted();
        GeneticAlgorithm geneticAlgorithm = new TimeLimitedAlgorithm().usesTimeLimit(TIME_LIMIT);
//        GeneticAlgorithm geneticAlgorithm = new GenerationLimitedAlgorithm().usesGenerationLimit(GENERATION_AMOUNT);

        Population population = geneticAlgorithm
                .startedAt(Instant.now().toEpochMilli())
                .documentsStatistic(DOCUMENTS_STATISTIC)
                .usesPrimarySequence(PRIMARY_SEQUENCE)
                .hasPopulationAmountOf(POPULATION_AMOUNT)
                .hasCrossoverRateOf(CROSSOVER_RATE)
                .hasMutationRateOf(MUTATION_RATE)
                .calculatesHammingDistance(CALC_HEMMING_DISTANCE)
                .usesSelectionAlgorithm(new FitnessProportionalSelectionAlgorithm())
//                .usesSelectionAlgorithm(new TunierFitnessProportionalSelectionAlgorithm(CANDIDATE_AMOUNT_PER_SELECTION))
//                .usesSelectionAlgorithm(new TunierBestFitnessSelectionAlgorithm(CANDIDATE_AMOUNT_PER_SELECTION))
//                .usesSelectionAlgorithm(new SigmaScalingSelectionAlgorithm())
                .generate();


        System.out.println("Calculation took: " + s.elapsed(TimeUnit.MILLISECONDS));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
