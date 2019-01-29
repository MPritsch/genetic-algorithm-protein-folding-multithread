package org.hda.gaf.threads;

import org.hda.gaf.algorithm.Population;
import org.hda.gaf.algorithm.evaluation.direction.RelativeDirection;
import org.hda.gaf.algorithm.geneticalgorithm.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static org.hda.gaf.DefaultOptions.*;

public class PopulationThread implements Runnable {

    private int threadNumber;
    private int neighborThread;
    private Population ownPopulation;
    private GeneticAlgorithm geneticAlgorithm;
    private Map<Integer, BlockingQueue<List<List<RelativeDirection>>>> queueMap;
    private BlockingQueue<List<RelativeDirection>> bestProteins;

    public PopulationThread(int threadNumber,
                            Population ownPopulation,
                            GeneticAlgorithm geneticAlgorithm,
                            Map<Integer, BlockingQueue<List<List<RelativeDirection>>>> queueMap,
                            BlockingQueue<List<RelativeDirection>> bestProteins) {
        this.threadNumber = threadNumber;
        this.ownPopulation = ownPopulation;
        this.geneticAlgorithm = geneticAlgorithm;
        this.queueMap = queueMap;
        this.bestProteins = bestProteins;


        if (threadNumber % 2 == 0) {
            this.neighborThread = ((threadNumber + 1) + THREAD_AMOUNT) % THREAD_AMOUNT;
        } else {
            this.neighborThread = ((threadNumber - 1) + THREAD_AMOUNT) % THREAD_AMOUNT;
        }
    }


    @Override
    public void run() {
        for (int j = 0; j < 5; j++) {
            geneticAlgorithm.runAlgorithm(ownPopulation);

            if (isAllowedToShareGenes(threadNumber)) {
                try {
                    List<List<RelativeDirection>> neighborGenepool = shareAndReceiveNeighborGenes();

                    addAndReplaceNeighborGenes(neighborGenepool);

                    if (threadNumber == 0) {
                        System.out.println(j + ". Iteration done");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(threadNumber + " Done. Sending/collecting best genoms.");

        collectBestPopulation(bestProteins, threadNumber, ownPopulation);

        System.out.println(threadNumber + " Share statistics");
        ThreadStatisticsSharer.shareStatistics(ownPopulation);
    }

    private void addAndReplaceNeighborGenes(List<List<RelativeDirection>> neighborGenepool) {
        List<List<RelativeDirection>> genepool = ownPopulation.getGenepool();
        for (List<RelativeDirection> genom : neighborGenepool) {
            genepool.remove(0);
            genepool.add(genom);
        }
    }

    private List<List<RelativeDirection>> shareAndReceiveNeighborGenes() throws InterruptedException {
        Collections.shuffle(ownPopulation.getGenepool());

        sendToNeighbor();
        return receiveFromNeighbor();
    }

    private boolean isAllowedToShareGenes(int threadNumber) {
        boolean isLastThread = (THREAD_AMOUNT - 1) == threadNumber;
        boolean isStraightThreadNumber = THREAD_AMOUNT % 2 == 0;

        return !isLastThread || isStraightThreadNumber;
    }

    private List<List<RelativeDirection>> receiveFromNeighbor() throws InterruptedException {
        BlockingQueue<List<List<RelativeDirection>>> neighborQueue = queueMap.get(neighborThread);

        return neighborQueue.take();
    }

    private void sendToNeighbor() throws InterruptedException {
        List<List<RelativeDirection>> ownGenepool = ownPopulation.getGenepool();

        List<List<RelativeDirection>> listToSend = new ArrayList<>();
        for (int i = 0; i < INDIVIDUAL_AMOUNT_TO_SEND; i++) {

            List<RelativeDirection> copyGenom = new ArrayList<>(ownGenepool.get(i));

            listToSend.add(copyGenom);
        }
        BlockingQueue<List<List<RelativeDirection>>> ownQueue = queueMap.get(threadNumber);
        ownQueue.put(listToSend);
    }

    private void collectBestPopulation(BlockingQueue<List<RelativeDirection>> bestProteins, int threadNumber, Population ownPopulation) {
        Population bestPopulation = new Population(THREAD_AMOUNT);

        try {
            if (threadNumber == 0) {
                bestPopulation.addGensToGenpool(ownPopulation.getBestProtein().getRelativeDirections());

                for (int from = 1; from < THREAD_AMOUNT; from++) {

                    List<RelativeDirection> bestProtein = bestProteins.take();
                    bestPopulation.addGensToGenpool(bestProtein);
                }


                bestPopulation.evaluate(PRIMARY_SEQUENCE);

                bestPopulation.saveResults(0);

                System.out.println("Received a new best protein: " + bestPopulation.getBestProtein().getValidNeighborCount());

            } else {
                List<RelativeDirection> bestProtein = ownPopulation.getBestProtein().getRelativeDirections();
                bestProteins.add(bestProtein);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
