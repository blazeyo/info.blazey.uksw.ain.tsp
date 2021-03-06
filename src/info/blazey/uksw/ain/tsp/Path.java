package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public abstract class Path implements Comparable<Path> {

  protected Graph graph;

  public Path(Graph graph) {
    this.graph = graph;
  }

  public boolean isValid() {
    if (this.getNodes().containsAll(graph.getNodes()) && this.getNodes().size() == graph.getNodes().size()) {
      return true;
    }
    return false;
  }

  public abstract boolean appendNode(Node t);

  public abstract void swapNodes(int index1, int index2);

  public abstract ArrayList<Node> getNodes();

  public abstract int getNodesCount();

  public abstract Edge getEdge(int index);

  public abstract double getDistance();

  public abstract Path getEmptyIndividual();

  public abstract void initializeWithRandomValue();

  @Override
  public abstract Object clone();

  public int compareTo(Path o) {
    if (this.getDistance() > o.getDistance()) {
      return 1;
    } else if (this.getDistance() == o.getDistance()) {
      return 0;
    } else {
      return -1;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    double distance = 0;
    for (int i = 0; i < getNodesCount() - 1; i++) {
      Edge e = getEdge(i);
      sb.append(e.toString()).append("\n");
      distance += e.getDistance();
    }
    ArrayList<Node> nodes = getNodes();
    distance += graph.getEdge(nodes.get(0), nodes.get(getNodesCount() - 1)).getDistance();

    sb.append("Tour length: " + distance);

    return sb.toString();
//    return "Path (" + this.getNodesCount() + ") length: " + this.getDistance();
  }

}
