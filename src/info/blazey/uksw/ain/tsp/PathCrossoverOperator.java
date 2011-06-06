package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author blazej
 */
public abstract class PathCrossoverOperator {
  
  private double crossoverChance;

  public PathCrossoverOperator(double crossoverChance) {
    this.crossoverChance = crossoverChance;
  }

  public void setCrossoverChance(double crossoverChance) {
    this.crossoverChance = crossoverChance;
  }

  private ArrayList<Path> getIndividualsThatWillHaveChildren(ArrayList<Path> population) {
    ArrayList<Path> parents = new ArrayList<Path>();

    for (Path p : population) {
      if (TSPGAController.nextDouble() < crossoverChance) {
        parents.add(p);
      }
    }
    
    return parents;
  }

  public ArrayList<Path> getOffspring(GenerationTSP generation) {
    ArrayList<Path> population = generation.getPopulation();
    ArrayList<Path> offspring = new ArrayList<Path>();
    
    int populationSize = population.size();

    Collections.sort(population);

    ArrayList<Path> parents = getIndividualsThatWillHaveChildren(population);

//    ArrayList<Path> initialParents = (ArrayList<Path>) parents.clone();
    ArrayList<Path> initialParents = new ArrayList<Path>(parents);

    while (parents.size() > 1) {
      int firstParentIndex = TSPGAController.nextInt(parents.size());
      Path firstParent = parents.get(firstParentIndex);
      parents.remove(firstParentIndex);

      int secondParentIndex = TSPGAController.nextInt(parents.size());
      Path secondParent = parents.get(secondParentIndex);
      parents.remove(secondParentIndex);

      offspring.addAll(getChildren(firstParent, secondParent));
    }

    // Add remaining parent
    if (parents.size() > 0) {
      offspring.add(parents.get(0));
    }

    while (offspring.size() < populationSize) {
      int firstParentIndex = TSPGAController.nextInt(initialParents.size());
      Path firstParent = initialParents.get(firstParentIndex);

      int secondParentIndex = TSPGAController.nextInt(initialParents.size());
      Path secondParent = initialParents.get(secondParentIndex);

      offspring.addAll(getChildren(firstParent, secondParent));
    }

    return offspring;
  }

  protected abstract ArrayList<Path> getChildren(Path parent1, Path parent2);

}
