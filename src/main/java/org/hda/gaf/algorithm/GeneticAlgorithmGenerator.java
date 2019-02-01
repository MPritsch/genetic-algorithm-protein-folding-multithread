package org.hda.gaf.algorithm;

import org.hda.gaf.algorithm.geneticalgorithm.GenerationLimitedAlgorithm;
import org.hda.gaf.algorithm.geneticalgorithm.GeneticAlgorithm;
import org.hda.gaf.algorithm.geneticalgorithm.TimeLimitedAlgorithm;
import org.hda.gaf.algorithm.selectionalgorithm.FitnessProportionalSelectionAlgorithm;

import java.time.Instant;
import java.util.List;

import static org.hda.gaf.DefaultOptions.*;

public class GeneticAlgorithmGenerator {

    public GeneticAlgorithm fromArgs(List<String> commands, int threadNumber) {
        printCommandLineOptions(threadNumber);

        GeneticAlgorithm geneticAlgorithm;

        geneticAlgorithm = getBaseAlgorithmFromArgs(commands, threadNumber);

        geneticAlgorithm
                .startedAt(Instant.now().toEpochMilli())
                .documentsStatistic(DOCUMENTS_STATISTIC)
                .usesPrimarySequence(PRIMARY_SEQUENCE)
                .hasPopulationAmountOf(POPULATION_AMOUNT)
                .hasCrossoverRateOf(CROSSOVER_RATE)
                .hasMutationRateOf(MUTATION_RATE)
                .usesSelectionAlgorithm(new FitnessProportionalSelectionAlgorithm())
                .calculatesHammingDistance(CALC_HEMMING_DISTANCE)
                .printsWhileGenerating(PRINT_WHILE_GENERATING);

        return geneticAlgorithm;
    }

    private GeneticAlgorithm getBaseAlgorithmFromArgs(List<String> commands, int threadNumber) {
        GeneticAlgorithm geneticAlgorithm;
        int timeCommandIndex = commands.indexOf("--time");
        int generationCommandIndex = commands.indexOf("--generation");

        if (timeCommandIndex != -1) {

            geneticAlgorithm = getTimeLimitedAlgorithm(threadNumber, commands, timeCommandIndex);

        } else if (generationCommandIndex != -1) {
            geneticAlgorithm = getGenerationLimitedAlgorithm(threadNumber, commands, generationCommandIndex);

        } else {
            geneticAlgorithm = getDefaultAlgorithm(threadNumber);
        }
        return geneticAlgorithm;
    }

    private GeneticAlgorithm getDefaultAlgorithm(int threadNumber) {
        GeneticAlgorithm geneticAlgorithm;
        geneticAlgorithm = new GenerationLimitedAlgorithm().usesGenerationLimit(GENERATION_AMOUNT / THREAD_EXCHANGE_TIMES);
        if (threadNumber == 0) {
            System.out.println("Executing default 'generation' limited algorithm with total " + GENERATION_AMOUNT + " per process.");
        }
        return geneticAlgorithm;
    }

    private GeneticAlgorithm getTimeLimitedAlgorithm(int threadNumber, List<String> commands, int timeCommandIndex) {
        GeneticAlgorithm geneticAlgorithm;
        long timeLimit = TIME_LIMIT;

        if (commands.size() > timeCommandIndex) {
            timeLimit = Long.valueOf(commands.get(timeCommandIndex + 1));
        }

        geneticAlgorithm = new TimeLimitedAlgorithm().usesTimeLimit(timeLimit / THREAD_AMOUNT);
        if (threadNumber == 0) {
            System.out.println("Executing 'time' limited algorithm with " + timeLimit + " ms per process.");
        }
        return geneticAlgorithm;
    }

    private GeneticAlgorithm getGenerationLimitedAlgorithm(int threadNumber, List<String> commands, int generationCommandIndex) {
        GeneticAlgorithm geneticAlgorithm;
        int generationAmount = GENERATION_AMOUNT;

        if (commands.size() > generationCommandIndex) {
            generationAmount = Integer.valueOf(commands.get(generationCommandIndex + 1));
        }

        geneticAlgorithm = new GenerationLimitedAlgorithm().usesGenerationLimit(generationAmount / THREAD_EXCHANGE_TIMES);
        if (threadNumber == 0) {
            System.out.println("Executing 'generation' limited algorithm with total " + generationAmount + " per process.");
        }
        return geneticAlgorithm;
    }

    private static void printCommandLineOptions(int threadNumber) {
        if (threadNumber == 0) {
            System.out.println("Following command line options available:\n" +
                    "# use default options 'generation' with " + GENERATION_AMOUNT + " generations\n" +
                    "java -jar <jar-path> --threads <thread amount>\n \n" +
                    "# specify total time spend per process\n" +
                    "java -jar <jar-path> --threads <thread amount> --time <time in ms>\n \n" +
                    "# specify total generation amount per process\n" +
                    "java -jar <jar-path> --threads <thread amount> --generation <generations>\n \n");
            System.out.flush();
        }
    }
}
