package main;

import lombok.Getter;
import lombok.Setter;
import main.evaluation.direction.RelativeDirection;
import main.evaluation.node.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 07.05.16.
 */
@Getter
@Setter
public class Population {

    List<List<RelativeDirection>> genpool;

    List<Structure> structures;

    public Population(int populationSize) {
        genpool = new ArrayList<>(populationSize);
        structures = new ArrayList<>(populationSize);
    }

    public void addGensToGenpool(List<RelativeDirection> gens){
        genpool.add(gens);
    }

    public void addStructure(Structure structure){
        structures.add(structure);
    }

    //TODO dont print if population had no good structure
    public Structure printStatusAndGetBestStructure(int currentGeneration){
        int totalFitness = 0;
        Structure bestProtein = structures.get(0);

        for (Structure structure : structures) {
            int fitness = structure.getFitness();
            totalFitness += fitness;
            if (fitness > bestProtein.getFitness()) {
                bestProtein = structure;
            }

        }
        float averageFitness = (float) totalFitness / (float) structures.size();

        System.out.println("Generation " + currentGeneration + ":");
        System.out.println("  Total Fitness: " + totalFitness);
        System.out.println("  Average Fitness " + averageFitness);
        System.out.println("  Best Protein with fitness: " + bestProtein.getFitness());

        if(bestProtein.getFitness() > 1){
            bestProtein.printStructure();
        }

        return bestProtein;
    }
}
