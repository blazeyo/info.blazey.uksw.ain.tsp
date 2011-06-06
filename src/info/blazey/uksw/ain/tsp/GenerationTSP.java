package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author blazej
 */
public abstract class GenerationTSP implements Generation {

  protected Graph graph;

  private ArrayList<Path> population;

  protected PathCrossoverOperator crossoverOperator;
  protected PathSelectionOperator selectionOperator;
  protected PathMutationOperator mutationOperator;

  private GenerationStatistics stats;

  public abstract Path getNewIndividual();
  public abstract Generation getNewGeneration(ArrayList<Path> population);

  public GenerationTSP(Graph graph, PathSelectionOperator selection, PathCrossoverOperator crossover, PathMutationOperator mutation) {
    this.graph = graph;
    this.population = new ArrayList<Path>();
    this.selectionOperator = selection;
    this.crossoverOperator = crossover;
    this.mutationOperator = mutation;
    stats = new GenerationStatisticsTSP(this);
  }

  public Graph getGraph() {
    return graph;
  }

  public void initialize(int parentsCount) {
    for (int i = 0; i < parentsCount; i++) {
      population.add(this.getNewIndividual());
    }
    Main.log("* TSP generation initialized. Population size: " + parentsCount);
  }

  protected void setPopulation(ArrayList<Path> population) {
    this.population = population;
  }

  public ArrayList<Path> getPopulation() {
    return population;
  }

  public GenerationStatistics getStatistics() {
    return stats;
  }

  public Generation getNext() {
    Main.log("Getting next population");
    ArrayList<Path> offspring = new ArrayList<Path>();

    // Selection
    population = selectionOperator.filter(population);

    // Crossover
    offspring = crossoverOperator.getOffspring(this);

    // Mutation
    int counter = 0;
    for (Path c : offspring) {
      if (mutationOperator.mutate()) {
        mutationOperator.performMutation(c);
        counter++;
      }
    }
    Main.log("Performed " + counter + " mutations");
    
    return this.getNewGeneration(offspring);
  }

  public Path getBestIndividual() {
    Collections.sort(population);
    return population.get(0);
  }

}
