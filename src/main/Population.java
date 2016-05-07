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
}
