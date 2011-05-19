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

    population.removeAll(parents);

    while (parents.size() > 0) {
      int firstParentIndex = TSPGAController.nextInt(parents.size());
      Path firstParent = parents.get(firstParentIndex);
      parents.remove(firstParentIndex);

      int secondParentIndex = TSPGAController.nextInt(parents.size());
      Path secondParent = parents.get(secondParentIndex);
      parents.remove(secondParentIndex);

      offspring.addAll(getChildren(firstParent, secondParent));
    }

    int i = 0;
    while (offspring.size() < populationSize) {
      offspring.add(population.get(i++));
    }

    return offspring;
  }

  protected abstract ArrayList<Path> getChildren(Path parent1, Path parent2);

  private Path getRandomParent(ArrayList<Path> population) {
    return population.get(TSPGAController.nextInt(population.size()));
  }

}
