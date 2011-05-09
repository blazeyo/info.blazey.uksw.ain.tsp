package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public class Node implements Comparable<Node> {

  private int number;

  public Node(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return Integer.toString(number);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Node) {
      Node t = (Node)obj;
      return this.number == t.getNumber();
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 79 * hash + this.number;
    return hash;
  }

  public int compareTo(Node o) {
    if (this.number < o.getNumber()) {
      return -1;
    } else if (this.number == o.getNumber()) {
      return 0;
    } else {
      return 1;
    }
  }

}
