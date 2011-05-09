package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public abstract class TSPGeneration implements Generation {

  protected Graph graph;

  private ArrayList<Path> population;

  protected PathCrossoverOperator crossoverOperator;
  protected PathSelectionOperator selectionOperator;
  protected PathMutationOperator mutationOperator;

  public abstract Path getNewIndividual();
  public abstract Generation getNewGeneration(ArrayList<Path> population);

  public TSPGeneration(Graph graph, PathSelectionOperator selection, PathCrossoverOperator crossover, PathMutationOperator mutation) {
    this.graph = graph;
    this.population = new ArrayList<Path>();
    this.selectionOperator = selection;
    this.crossoverOperator = crossover;
    this.mutationOperator = mutation;
  }

  public void initialize(int parentsCount) {
    for (int i = 0; i < parentsCount; i++) {
      population.add(this.getNewIndividual());
    }
  }

  protected void setPopulation(ArrayList<Path> population) {
    this.population = population;
  }

  public Generation getNext() {
    ArrayList<Path> offspring = new ArrayList<Path>();

    // Selection
    population = selectionOperator.filter(population);

    // Crossover
    for (Path p : population) {
      offspring.addAll(crossoverOperator.getChildren(p, this.getRandomParent(p)));
    }

    // Mutation
    for (Path c : offspring) {
      if (mutationOperator.mutate()) {
        mutationOperator.performMutation(c);
      }
    }

    return this.getNewGeneration(offspring);
  }

  private Path getRandomParent(Path except) {
    ArrayList<Path> temp = (ArrayList<Path>) population.clone();
    temp.remove(except);
    return temp.get(TSPGAController.nextInt(temp.size()));
  }

}
