/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.blazey.uksw.ain.tsp;

/**
 *
 * @author blazej
 */
public class NodeWithCoordinates extends Node {

  private double x;
  private double y;

  public NodeWithCoordinates(int number, double x, double y) {
    super(number);
    this.x = x;
    this.y = y;
  }

  double getX() {
    return x;
  }

  double getY() {
    return y;
  }

  public double getDistanceTo(NodeWithCoordinates town) {
    double x1, x2;
    double y1, y2;

    if (x < town.getX()) {
      x1 = x;
      x2 = town.getX();
    } else {
      x1 = town.getX();
      x2 = x;
    }
    if (y < town.getY()) {
      y1 = y;
      y2 = town.getY();
    } else {
      y1 = town.getY();
      y2 = y;
    }

    double xDistance = x2 - x1;
    double yDistance = y2 - y1;

    return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
  }

}
