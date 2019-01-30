# Genetic algorithm protein folding using MPJ
This project was developed for a genetic algorithm course at the "Hochschule Darmstadt". \
It was extended with Java Multithreading to allow execution with more cores.



The input is a string of 0s and 1s. 
The goal is to fold the sequence optimizing the n4 neighbors of the 1s without an overlap in the chain.
All this happens on a 2D plain with 3 possible directions: straight, left, right.

## Possible settings

In the default options (`org.hda.gaf.DefaultOptions`) there are several settings you can modify/comment in:
* Choose one of the genetic algorithms:
    * Time limited algorithm (e.g. generate for 20s, then stop) 
    * Generation limited algorithm (e.g. generate 100 generations, then stop)
* Selection algorithms:
    * Fitness proportional (Every individual has a chance to get chosen. A higher fitness results in a better chance to get chosen)
    * Tunier fitness proportional (Same as fitness proportional but pairing individuals in a tunier and choosing every turn)
    * Tunier best fitness (Pairing individuals in a tunier and choosing the one with the best fitness)
* Population amount: Individual amount per generation
* Mutate rate: 
    Percent of how many genes of the total population should be mutated after selection. 
    The mutation includes changing a left turn to either a straight, right or left turn.
    
    E.g. Having 100 genes per individual in a population with 10 individuals means we have a total of 1000 genes. 
    With a mutation rate of 2% a total 20 of the genes gets modified every generation.
* Crossover rate: 
    Percent of how many individuals in a population should do a crossover every generation.
    Using a simple one point crossover (splitting the gene chain anywhere and switching it with another partner in the population).
    
    E.g. Having a population of 100 individuals and a crossover rate of 20% means that 20 individuals crossover every generation.
 
* Print while generating:
    Activates a GUI displaying the currently best found protein and some stats. For better performance deactivate it.
    
* Thread exchange times:
    How many times should the threads share part of their current population with another thread.
    
    E.g. Generation amount is set to 100 and the MPJ exchange time to 5.
    That means every 20th generation the processes exchange 1/5th of their population with another process.

## Other projects
Base: \
This is the base implementation of the protein folding project.
It features a GUI displaying current stats and can be executed on a single machine. \
https://github.com/MPritsch/genetic-algorithm-protein-folding

MPJ Express: \
MPJ is a java implementation of MPI (Message Passing Interface).
It can be used to distribute the computing workload on a Cluster with multiple CPU's. 
Part of the generated population is shared with other MPJ processes to allow for more variety. \
https://github.com/MPritsch/genetic-algorithm-protein-folding-mpj

### Generate jar
You can package this project via maven:\
`mvn clean package`
The jar should then be available at `target/genetic-algorithm-protein-folding-multithread-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Execution
Following command line options available:

Use default options 'generation' with the amount of generations defined in `org.hda.gaf.DefaultOptions`: \
`java -jar <jar-path>`
 
Specify total time spend per process: \
`java -jar <jar-path> --time <time in ms>`
 
Specify total generation amount per process: \
`java -jar <jar-path> --generation <generations>`

You can also change the default thread amount with the option `--threads <thread-amount>`.
