package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class PathCrossoverOperatorOX extends PathCrossoverOperator {

  public PathCrossoverOperatorOX(double crossoverChance) {
    super(crossoverChance);
  }

  private int[] cuttingPoints;

  protected ArrayList<Path> getChildren(Path parent1, Path parent2) {
    // Get cutting points
    cuttingPoints = getCuttingPoints(parent1.getNodesCount());

    // Get children
    ArrayList<Path> children = new ArrayList<Path>();
    children.add(getChild(parent1, parent2));
    children.add(getChild(parent2, parent1));

    return children;
  }

  private int[] getCuttingPoints(int length) {
    int[] points = new int[2];
    points[0] = TSPGAController.nextInt(length);
    do {
      points[1] = TSPGAController.nextInt(length);
    } while (points[0] == points[1]);

    if (points[0] > points[1]) {
      int tmp = points[0];
      points[0] = points[1];
      points[1] = tmp;
    }

    return points;
  }

  private Path getChild(Path parent1, Path parent2) {
    Path child = parent1.getEmptyIndividual();
    ArrayList<Node> p1nodes = parent1.getNodes();
    ArrayList<Node> p2nodes = parent2.getNodes();
    for (int i = cuttingPoints[0]; i < cuttingPoints[1]; i++) {
      Node n1 = p1nodes.get(i);
      child.appendNode(n1);
      p2nodes.remove(n1);
    }

    for (Node n2 : p2nodes) {
      child.appendNode(n2);
    }

    return child;
  }

  @Override
  public String toString() {
    return "OX";
  }

}
