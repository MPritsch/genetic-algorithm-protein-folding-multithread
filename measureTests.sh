#!/bin/bash

ga_alg() {
    THREADS=$1
    GENERATIONS=$2

    echo -e "\n\n\n----------------------- NEXT ------------------------\n\n\n"
    echo -e "Using $THREADS threads calculating $GENERATIONS generations per process\n"
    echo -e "\nRun 1\n\n"
    salloc -N 1 -n ${THREADS} java -jar ~/java-mpj/genetic-algorithm-protein-folding-multithread-1.0-SNAPSHOT-jar-with-dependencies.jar --generation ${GENERATIONS} --threads ${THREADS}
    echo -e "\nRun 2\n\n"
    salloc -N 1 -n ${THREADS} java -jar ~/java-mpj/genetic-algorithm-protein-folding-multithread-1.0-SNAPSHOT-jar-with-dependencies.jar --generation ${GENERATIONS} --threads ${THREADS}
    echo -e "\nRun 3\n\n"
    salloc -N 1 -n ${THREADS} java -jar ~/java-mpj/genetic-algorithm-protein-folding-multithread-1.0-SNAPSHOT-jar-with-dependencies.jar --generation ${GENERATIONS} --threads ${THREADS}
}

runtests() {
    echo -e "\n\n\n##################### NEW TEST SUITE ##########################\n\n\n"

    THREAD_COUNT_ARRAY=($@)

    echo -e "Using $THREAD_COUNT_ARRAY threads"

    for i in "${THREAD_COUNT_ARRAY[@]}"
    do
        ga_alg $i 10
        ga_alg $i 20
        ga_alg $i 40
        ga_alg $i 80
        ga_alg $i 160
    done
}

echo -e "time in ms;mpj process amount;total generation count;total calculated individuals;time limit;generation limit;given commands;\n" >> results.csv


echo -e "\n\n\n#--------###################------# MULTITHREADING #----------########################---------#\n\n\n"

THREAD_ARRAY=( 1 2 4 8 16 28 32 56 )

runtests ${THREAD_ARRAY[@]}
