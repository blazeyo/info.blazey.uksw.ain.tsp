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

    ArrayList<Node> nodes = (ArrayList<Node>) graph.getNodes().clone();
//    Main.log("Node count: " + nodes.size());
    // Get random starting node
    Node from = nodes.get(TSPGAController.nextInt(nodes.size()));
    nodes.remove(from);
    path.appendNode(from);
    while (nodes.size() > 1) {
      Node destination = null;
      double minDistance = Double.MAX_VALUE;
      for (Node n : nodes) {
        Edge e = graph.getEdge(from, n);
        if (e.getDistance() < minDistance) {
          minDistance = e.getDistance();
          destination = n;
        }
      }
//      Main.log(destination.toString());
      nodes.remove(destination);
      path.appendNode(destination);
      from = destination;
    }
//    Main.log("");
    path.appendNode(nodes.get(0));
//    path.initializeWithRandomValue();
    return path;
  }

  @Override
  public Generation getNewGeneration(ArrayList<Path> population) {
    GenerationTSPSciezkowa n = new GenerationTSPSciezkowa(this.graph, this.selectionOperator, this.crossoverOperator, this.mutationOperator);
    n.setPopulation(population);
    return n;
  }

}
