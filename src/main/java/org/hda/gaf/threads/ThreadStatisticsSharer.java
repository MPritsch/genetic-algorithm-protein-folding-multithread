package org.hda.gaf.threads;

import org.hda.gaf.algorithm.Population;

public abstract class ThreadStatisticsSharer {

    private static Integer globalGenerationCount = 0;

    public static int getGlobalGenerationCount() {
        return globalGenerationCount;
    }

    public static void shareStatistics(Population population) {
        int localGenerationCount = population.getStatistic().getGeneration();

        addToCount(localGenerationCount);
    }

    private static synchronized void addToCount(int localGenerationCount) {
        globalGenerationCount += localGenerationCount;
    }
}
