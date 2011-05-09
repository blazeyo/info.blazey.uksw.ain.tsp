package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public class Edge {

  private Node from, to;
  private double distance;

  public Edge(Node from, Node to, double distance) {
    this.from = from;
    this.to = to;
    this.distance = distance;
  }

  public Node getFrom() {
    return from;
  }

  public Node getTo() {
    return to;
  }

  public double getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return "edge from " + from + " to " + to + ". Distance: " + Double.toString(distance);
  }

}
