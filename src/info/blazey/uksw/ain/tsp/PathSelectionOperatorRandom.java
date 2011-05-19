/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.blazey.uksw.ain.tsp;

import java.util.ArrayList;

/**
 *
 * @author blazej
 */
public class PathSelectionOperatorRandom implements PathSelectionOperator {

  public ArrayList<Path> filter(ArrayList<Path> paths) {
    ArrayList<Path> offspring = new ArrayList<Path>();

    for (Path p : paths) {
      Path newPath = p.getEmptyIndividual();
      p.initializeWithRandomValue();
      offspring.add(p);
    }

    return offspring;
  }

}
