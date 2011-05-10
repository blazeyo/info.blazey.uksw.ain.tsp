package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class PathSciezkowa extends Path {

  private ArrayList<Node> path;
  
  public PathSciezkowa(Graph graph) {
    super(graph);
    path = new ArrayList<Node>();
  }

  public ArrayList<Node> getPath() {
    return path;
  }

  /**
   * Adds a node to the end of path.
   * @param t Node to be appended.
   * @return success
   */
  public boolean appendNode(Node t) {
    if (!path.contains(t)) {
      // Append it.
      path.add(t);
      return true;
    } else {
      Main.log("Node already exists");
    }
    return false;
  }

  private boolean contains(Node targetNode) {
    for (Node n : path) {
      if (n.getNumber() == targetNode.getNumber()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ArrayList<Node> getNodes() {
    return new ArrayList<Node>(this.path);
  }

  public int getNodesCount() {
    return path.size();
  }

  public Edge getEdge(int index) {
    if (path.size() > index + 1) {
      Node from = path.get(index);
      Node to = path.get(index + 1);
      return graph.getEdge(from, to);
    }
    return null;
  }

  public double getDistance() {
    double distance = 0;
    Node from = null;
    for (Node to : path) {
      if (from == null) {
        from = to;
        continue;
      }
      if (graph.getEdge(from, to) == null) {
        Main.log("* ERROR * No such edge in graph " + graph.getName() + ". From node " + from.getNumber() + " to node " + to.getNumber());
        Main.log("\tPath is valid: " + isValid());
        Main.log("\tPath: " + path.toString());
      } else {
        
      }
      distance += graph.getEdge(from, to).getDistance();
    }
    return distance;
  }

  public Path getEmptyIndividual() {
    return new PathSciezkowa(graph);
  }

  public void initializeWithRandomValue() {
    ArrayList<Node> towns = graph.getNodes();
//    Main.log("Int sequence:");
//    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < graph.getSize(); i++) {
      int random = TSPGAController.nextInt(towns.size());
      Node t = towns.get(random);
      towns.remove(t);
      this.appendNode(t);

//      sb.append(random + " ");
    }
//    Main.log(sb.toString());
  }

  @Override
  public void swapNodes(int index1, int index2) {
    Node tmp = path.get(index1);
    changeNodeAtIndex(index1, path.get(index2));
    changeNodeAtIndex(index2, tmp);
  }

  /**
   * Inserts element at index position and returns current one.
   * @param index
   * @return Node
   */
  private void changeNodeAtIndex(int index, Node node) {
    path.remove(index);
    path.add(index, node);
  }

}
