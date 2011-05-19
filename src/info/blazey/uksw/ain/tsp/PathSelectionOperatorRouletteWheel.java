/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author blazej
 */
public class PathSelectionOperatorRouletteWheel implements PathSelectionOperator {

  public ArrayList<Path> filter(ArrayList<Path> paths) {
    // Get max distance
    double maxDistance = 0;
    for (Path p : paths) {
      if (p.getDistance() > maxDistance) {
        maxDistance = p.getDistance();
      }
    }

    // Count sum of all paths distances.
    double distance = 0;
    for (Path p : paths) {
      distance += maxDistance - p.getDistance();
    }

    // Fill rulette wheel with distribution.
    Map<Double, Path> wheel = new HashMap<Double, Path>();
    double distribution = 0;
    for (Path p : paths) {
      distribution += (maxDistance - p.getDistance()) / distance;
      wheel.put(distribution, p);
    }

    // Perform selection
    double randomNumber;
    ArrayList<Path> result = new ArrayList<Path>();
    for (int i = 0; i < paths.size(); i++) {
      randomNumber = TSPGAController.nextDouble();
      // Start from first path as it is also first in wheel map.
      Path winner = paths.get(0);
      for (double dst : wheel.keySet()) {
        if (randomNumber < dst) {
          break;
        }
        winner = wheel.get(dst);
      }
      result.add((Path)winner.clone());
    }

    return result;
  }

  @Override
  public String toString() {
    return "Roulette wheel";
  }

}
