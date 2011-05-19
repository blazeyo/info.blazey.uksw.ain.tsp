package info.blazey.uksw.ain.tsp;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class PathSciezkowaCrossoverOperatorWithEdgeRecombination extends PathCrossoverOperator {

  public PathSciezkowaCrossoverOperatorWithEdgeRecombination(double crossoverChance) {
    super(crossoverChance);
  }

  private ArrayList<Path> parents;
  private HashMap<Node, ArrayList<Node>> neighboursList;
  private ArrayList<Path> children;

  /**
   * Get a pair of children.
   * @param parent1
   * @param parent2
   * @return children
   */
  protected ArrayList<Path> getChildren(Path parent1, Path parent2) {
    parents = new ArrayList<Path>();
    parents.add(parent1);
    parents.add(parent2);
    neighboursList = new HashMap<Node, ArrayList<Node>>();
    children = new ArrayList<Path>();

    fillNeighboursList();

    for (Path parent : parents) {
      children.add(getChild(parent));
    }

    return children;
  }

  /**
   * Get a single child.
   * @param parent Parent to start from
   * @return child
   */
  private PathSciezkowa getChild(Path parent) {
    PathSciezkowa child = (PathSciezkowa) parent.getEmptyIndividual();

    // Get the first node of parent path.
    Node t = parent.getEdge(0).getFrom();

    // Add all nodes to child's path.
    while (child.getNodesCount() != parent.getNodesCount() && t != null) {
      // Add previously chosen node.
      if (child.appendNode(t)) {
        // Find next node.
        t = getUnvisitedNodeWithLeastNeighbours(t, child.getNodes());
        if (t == null) {
          t = getRandomUnvisitedNode(child.getNodes());
        }
      } else {
       break;
      }
    }

    if (!child.isValid()) {
      child = getChild(parent);
      Main.log("Path not valid. Creating new child");
    }

    return child;
  }

  /**
   * Return node neighbour, wchich hasn't been yet visited and has the least number of neighbours himself.
   * @param from Starting node.
   * @param alreadyVisited List of already visited nodes.
   * @return node
   */
  private Node getUnvisitedNodeWithLeastNeighbours(Node from, ArrayList<Node> alreadyVisited) {
    ArrayList<Node> candidates = new ArrayList<Node>();
    ArrayList<Node> neighbours = neighboursList.get(from);
    
    // Remove already visited nodes.
    neighbours.removeAll(alreadyVisited);

    // Find nodes with the least number of neighbours.
    int min = Integer.MAX_VALUE;
    for (Node candidate : neighbours) {
      int count = getTownNeighboursCount(candidate);
      if (count < min) {
        min = count;
        candidates.clear();
        candidates.add(candidate);
      } else if (count == min) {
        candidates.add(candidate);
      }
    }

    // If there is more than one node, choose one randomly.
    return this.getRandomNode(candidates);
  }

  /**
   * Return random node.
   * @param alreadyVisited Illegal nodes.
   * @return node
   */
  private Node getRandomUnvisitedNode(ArrayList<Node> alreadyVisited) {
    // Get all available nodes.
    ArrayList<Node> nodes = new ArrayList<Node>(neighboursList.keySet());

    // Remove already visited ones.
    nodes.removeAll(alreadyVisited);

    return this.getRandomNode(nodes);
  }

  private Node getRandomNode(ArrayList<Node> nodes) {
    if (nodes.size() > 0) {
      return nodes.get(TSPGAController.nextInt(nodes.size()));
    }
    return null;
  }

  private int getTownNeighboursCount(Node from) {
    return neighboursList.get(from).size();
  }

  private void fillNeighboursList() {
    // For each of the parents
    for (Path parent : parents) {
      int index = 0;
      Edge edge;
      // Add all arcs
      while ((edge = parent.getEdge(index++)) != null) {
        addNeighbours(edge);
      }
    }
  }

  private void addNeighbours(Edge edge) {
    addNeighbour(edge.getFrom(), edge.getTo());
    addNeighbour(edge.getTo(), edge.getFrom());
  }

  private void addNeighbour(Node from, Node to) {
    ArrayList<Node> neighbours = neighboursList.get(from);
    if (neighbours == null) {
      neighbours = new ArrayList<Node>();
    }
    if (neighbours.contains(to) == false) {
      neighbours.add(to);
    }
    neighboursList.put(from, neighbours);
  }

  @Override
  public String toString() {
    return "Edge recombination";
  }

  class PathCrossoverException extends Exception {

  }

}
