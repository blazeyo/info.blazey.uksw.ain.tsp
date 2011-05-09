package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class GenerationTSPSciezkowa extends GenerationTSP {

  public GenerationTSPSciezkowa(Graph graph, PathSelectionOperator selection, PathCrossoverOperator crossover, PathMutationOperator mutation) {
    super(graph, selection, crossover, mutation);
  }

  @Override
  public Path getNewIndividual() {
    PathSciezkowa path = new PathSciezkowa(this.graph);
    path.initializeWithRandomValue();
    return path;
  }

  @Override
  public Generation getNewGeneration(ArrayList<Path> population) {
    GenerationTSPSciezkowa n = new GenerationTSPSciezkowa(this.graph, this.selectionOperator, this.crossoverOperator, this.mutationOperator);
    n.setPopulation(population);
    return n;
  }

}
