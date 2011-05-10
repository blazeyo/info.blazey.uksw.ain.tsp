package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

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

  public ArrayList<Path> getOffspring(ArrayList<Path> population) {
    ArrayList<Path> offspring = new ArrayList<Path>();
    int populationSize = population.size();
    population = getIndividualsThatWillHaveChildren(population);

    int limit = population.size() / 2;
    for (int i = 0; i < limit; i++) {
      Path firstParent = getRandomParent(population);
      population.remove(firstParent);
      Path secondParent = getRandomParent(population);
      population.remove(secondParent);

      offspring.addAll(getChildren(firstParent, secondParent));
    }
    Main.log("Performed " + limit + " crossovers");
    while (offspring.size() != populationSize) {
      Path randomIndividual = offspring.get(0).getEmptyIndividual();
      randomIndividual.initializeWithRandomValue();
      offspring.add(randomIndividual);
    }

    return offspring;
  }

  protected abstract ArrayList<Path> getChildren(Path parent1, Path parent2);

  private Path getRandomParent(ArrayList<Path> population) {
    return population.get(TSPGAController.nextInt(population.size()));
  }

}
