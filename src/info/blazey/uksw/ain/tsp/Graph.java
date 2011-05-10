package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author blazej
 */
public abstract class Graph {

  private String name;
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return this.name;
  }

  private String comment;
  public void setComment(String comment) {
    this.comment = comment;
  }
  public String getComment() {
    return this.comment;
  }

  private int size;
  public void setSize(int size) {
    this.size = size;
  }
  public int getSize() {
    return edges.keySet().size();
  }

  private HashMap<Node, ArrayList<Edge>> edges;

  public Graph() {
    edges = new HashMap<Node, ArrayList<Edge>>();
  }

  public abstract void addEdge(Node from, Node to, double distance) throws Exception;

  protected boolean edgeExists(ArrayList<Edge> townEdges, Node to) {
    // Check if this edge has not been inserted
    for (Edge e : townEdges) {
      if (e.getTo().equals(to)) {
        return true;
      }
    }
    return false;
  }
  
  protected ArrayList<Edge> getTownEdges(Node from) {
    ArrayList<Edge> townEdges = edges.get(from);
    // Initialize edges vector when inserting new town.
    if (townEdges == null) {
      townEdges = new ArrayList<Edge>();
    }
    return townEdges;
  }

  protected void setTownEdges(Node from, ArrayList<Edge> newTownEdges) {
    edges.put(from, newTownEdges);
  }

  public Edge getEdge(Node from, Node to) {
    ArrayList<Edge> townEdges = getTownEdges(from);
    for (Edge e : townEdges) {
      if (e.getTo().equals(to)) {
        return e;
      }
    }
    return null;
  }

  public ArrayList<Node> getNodes() {
    return new ArrayList<Node>(edges.keySet());
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (Node n : getNodes()) {
      for (Edge e : getTownEdges(n)) {
        output.append(e.toString()).append("\n");
      }
    }
    return output.toString();
  }

  class EdgeExistsException extends Exception {

    public EdgeExistsException() { }

  }

}
