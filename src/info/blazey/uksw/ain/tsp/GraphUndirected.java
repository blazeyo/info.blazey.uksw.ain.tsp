package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class GraphUndirected extends Graph {

  public void addEdge(Node from, Node to, double distance) throws EdgeExistsException {
    ArrayList<Edge> fromEdges = this.getTownEdges(from);
    ArrayList<Edge> toEdges = this.getTownEdges(to);

    if (edgeExists(fromEdges, to) || edgeExists(toEdges, from)) {
      throw new EdgeExistsException();
    } else {
      fromEdges.add(new Edge(from, to, distance));
      toEdges.add(new Edge(to, from, distance));
    }

    this.setTownEdges(from, fromEdges);
    this.setTownEdges(to, toEdges);
  }

}
