package org.hda.gaf.threads;

import org.hda.gaf.DefaultOptions;
import org.hda.gaf.algorithm.GeneticAlgorithmGenerator;
import org.hda.gaf.algorithm.Population;
import org.hda.gaf.algorithm.evaluation.direction.RelativeDirection;
import org.hda.gaf.algorithm.geneticalgorithm.GeneticAlgorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.hda.gaf.DefaultOptions.THREAD_AMOUNT;
import static org.hda.gaf.DefaultOptions.THREAD_EXCHANGE_TIMES;

public class ThreadAlgorithmExecutor {

    private List<String> commands;

    public ThreadAlgorithmExecutor(String[] args) {
        this.commands = Arrays.asList(args);
    }

    public Population executeAndGetBestPopulation() {

        setThreadAmountFromCommand();

        Map<Integer, Population> populationMap = new ConcurrentHashMap<>(THREAD_AMOUNT);
        Map<Integer, BlockingQueue<List<List<RelativeDirection>>>> queueMap = new HashMap<>(DefaultOptions.THREAD_AMOUNT);
        BlockingQueue<List<RelativeDirection>> bestProteins = new ArrayBlockingQueue<>(DefaultOptions.THREAD_AMOUNT);

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            queueMap.put(i, new ArrayBlockingQueue<>(THREAD_EXCHANGE_TIMES));
        }

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < THREAD_AMOUNT; i++) {

            final int threadNumber = i;

            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithmGenerator().fromArgs(commands, threadNumber);

            Population ownPopulation = geneticAlgorithm.generateStartPopulation();
            populationMap.put(i, ownPopulation);
            queueMap.put(i, new ArrayBlockingQueue<>(THREAD_EXCHANGE_TIMES));

            PopulationThread task = new PopulationThread(threadNumber, ownPopulation, geneticAlgorithm, queueMap, bestProteins);

            es.execute(task);
        }

        es.shutdown();
        try {
            es.awaitTermination(3, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return populationMap.get(0);
    }

    private void setThreadAmountFromCommand() {
        int threadCommandIndex = commands.indexOf("--threads");

        if (threadCommandIndex != -1) {
            if (commands.size() > threadCommandIndex) {
                THREAD_AMOUNT = Integer.valueOf(commands.get(threadCommandIndex + 1));
            }
        }

        System.out.println(String.format("Starting %s threads.", THREAD_AMOUNT));
    }
}
